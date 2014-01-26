package game;

import java.awt.Dimension;

import ui.UI;

/**
 * The game. Controls the game grid, and the rest of the game.
 * 
 * @author littlewoo
 *
 */
public class Game {
	private final Dimension size;
	private final Vector centre;
	
	private Piece[][] grid;
	private UI ui;
	private Timer timer;
	
	private Vector cursor;
	private Piece activePiece;
	
	private boolean acceptingInput;
	private boolean running;
	
	private int score;
	private int level;
	
	/**
	 * Creates a new game. For now, this is just a grid with Pieces for 
	 * demonstration/testing.
	 * 
	 * @param ui the user interface which will display this game.
	 */
	public Game(UI ui, Dimension size) {
		this.ui = ui;
		this.size = size;
		timer = new Timer();
		centre = new Vector(size.width / 2, 0);
		
		grid = new Piece[size.width][size.height];
		ui.updateGrid(grid);
		
		acceptingInput = true;
		running = true;
		
		score = 0;
		level = 0;
		
		gameLoop();
		
	}
	
	/**
	 * The game's main loop
	 */
	private void gameLoop()
	{
		newPiece();
		Thread t = new Thread() {
			public void run() {
				while (true) {
					while (running) {
						timer.awaitNextTick();
						tick();
					}
				}
			}
		};
		t.start();
	}
	
	/**
	 * Wait for a tick, then drop the cursor 1 block.
	 * 
	 */
	private void tick() {
		moveCursor(new Vector(0,1));
		if (isSettled()) {
			timer.awaitNextTick();
			if (isSettled()) {
				newPiece();
				checkLines();
			}
		}
	}
	
	/** 
	 * Check to see whether there are any lines, and remove them if there are.
	 */
	public void checkLines() {
		boolean[] lines = new boolean[grid[0].length];
		boolean line = false;
		for (int y=0; y<lines.length; y++) {
			lines[y] = true;
			for (int x=0; x<grid.length; x++) {
				if (grid[x][y] == null) {
					lines[y] = false;
					line = true;
					break;
				}
			}
		}
		if (line) {
			for (int i=0; i<lines.length; i++) {
				if (lines[i]) {
					line(i);
				}
			}
		}
	}
	
	private void line(int index) {
		score ++;
		ui.updateScore(score);
		if (score % 10 == 0) {
			level ++;
			timer.levelUp();
			ui.levelUp(level);
		}
		removeLine(index);
	}
	
	/**
	 * Remove a line from the grid, and move all lines above it down.
	 * 
	 * @param index the index of the line to remove
	 */
	private void removeLine(int index) {
		deletePiece();
		for (int i=index; i>0; i--) {
			for (int j=0; j<grid.length; j++) {
				grid[j][i] = grid[j][i-1];
			}
		}
		placePiece();
		ui.updateGrid(grid);
	}
	
	
	/**
	 * Drop the current piece, as a result of player action. This action causes
	 * the current piece to drop rapidly. Once it has started, the player cannot
	 * give further input until the dropped piece has settled.
	 * 
	 */
	public void drop() {
		acceptingInput = false;
		while (!isSettled()) {
			moveCursor(new Vector(0,1));
			timer.await(20);
		}
		acceptingInput = true;
	}
	
	/**
	 * Rotate the active piece through 90 degrees
	 */
	public void rotate() {
		deletePiece();
		activePiece.rotate();
		if (!checkNewPlacement(getActivePieceLoc())) {
			activePiece.reverseRotate();
		}
		placePiece();
		ui.updateGrid(grid);
	}
	
	/** 
	 * Move the current piece as a result of player action
	 * 
	 * @param offset the vector by which the current piece is moved
	 */
	public void movePiece(Vector offset) {
		if (acceptingInput) {
			moveCursor(offset);
		}
	}
	
	/**
	 * Move the cursor, and with it the active piece.
	 * 
	 * @param offset the vector by which the cursor is moved
	 */
	private void moveCursor(Vector offset) {
		if (checkMove(offset)) {
			deletePiece();
			cursor = cursor.add(offset);
			placePiece();
			ui.updateGrid(grid);
		}
	}
	
	/**
	 * Check that a move is valid
	 * 
	 * @param offset the move
	 */
	public boolean checkMove(Vector offset) {
		Vector[] v = getActivePieceLoc();
		Vector[] nv = new Vector[v.length];
		for (int i=0; i<v.length; i++) {
			nv[i] = v[i].add(offset);
		}
		return checkNewPlacement(nv);
	}
	
	/**
	 * Check that a placement of the active piece is valid: i.e. that it is 
	 * entirely within the bounds of the grid and it does not intersect any
	 * existing pieces.
	 * 
	 * @param offsets the new offsets of the placed piece.
	 */
	private boolean checkNewPlacement(Vector[] offsets) {
		for (Vector v : offsets) {
			if (v.x < 0 || v.x >= size.width || 
				v.y < 0 || v.y >= size.height ||
				(grid[v.x][v.y] != null && grid[v.x][v.y] != activePiece)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Get the current active piece's offsets, in their current location in the
	 * grid.
	 */
	private Vector[] getActivePieceLoc() {
		final Vector[] v = activePiece.getOffsets();
		Vector[] r = new Vector[v.length];
		for (int i=0; i<v.length; i++) {
			r[i] = v[i].add(cursor);
		}
		return r;
	}

	/**
	 * Place the cursor at the top middle, and place a new piece there.
	 */
	private void newPiece()
	{
		cursor = centre;
		activePiece = Piece.getRandomPiece();
		// make sure the new piece is always at the top, but not above it.
		Vector[] v = getActivePieceLoc();
		for (int i=0; i<v.length; i++) {
			if (v[i].y < 0) {
				if (v[i].y == -1) {
					cursor = new Vector(cursor.x, cursor.y+1);
				} else {
					cursor = new Vector(cursor.x, cursor.y+2);
				}
				 
			}
		}
		v = getActivePieceLoc();
		for (int i=0; i<v.length; i++) {
			if (grid[v[i].x][v[i].y] != null) {
				gameOver();
			}
		}
		placePiece();
	}
	
	/**
	 * Remove the active piece from the grid. This assumes that the active
	 * piece is referenced at each of its offsets from the cursor's current 
	 * location, and nowhere else.
	 */
	private void deletePiece() {
		editPiece(null);
	}
	
	/**
	 * Place the active piece at the cursor on the grid
	 */
	private void placePiece() {
		editPiece(activePiece);
	}
	
	/** 
	 * Replace each of the references for the offsets from the active piece
	 * with another piece. Used for placing and deleting pieces.
	 * 
	 * @param piece the piece to place (null to delete)
	 */
	private void editPiece(Piece p) {
		for (Vector v : getActivePieceLoc()) {
			grid[v.x][v.y] = p;
		}
	}
	
	/**
	 * Check whether the active piece is resting on the bottom of the grid or
	 * on another piece
	 * 
	 * @return true if it is at the bottom
	 */
	private boolean isSettled()
	{
		for (Vector v : getActivePieceLoc()) {
			Vector below = v.add(new Vector(0,1));
			if (v.y == size.height -1 ||
				grid[below.x][below.y] != null &&
				grid[below.x][below.y] != activePiece) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Clear the grid of all pieces.
	 */
	public void clearGrid()
	{
		for (int x=0; x<size.width; x++) {
			for (int y=0; y<size.height; y++) {
				grid[x][y] = null;
			}
		}
	}
	
	/**
	 * Game over - what happens when there is no room for a new piece.
	 */
	private void gameOver() {
		running = false;
		acceptingInput = false;
		ui.gameOver();
	}
	
	/**
	 * Set the value of whether the game is running or not.
	 * 
	 * @param value whether the game is running
	 */
	public void setRunning(boolean value) {
		running = value;
	}
}
