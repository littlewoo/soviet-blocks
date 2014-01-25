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
			}
		}
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
		for (Vector v : activePiece.getOffsets()) {
			Vector nv = v.add(offset).add(cursor);
			if (nv.x < 0 || nv.x >= size.width ||
				nv.y < 0 || nv.y >= size.height ||
				(grid[nv.x][nv.y] != null &&
				grid[nv.x][nv.y] != activePiece)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Place the cursor at the top middle, and place a new piece there.
	 */
	private void newPiece()
	{
		cursor = centre;
		activePiece = Piece.getRandomPiece();
		// make sure the new piece is always at the top, but not above it.
		Vector[] v = activePiece.getOffsets();
		for (int i=0; i<v.length; i++) {
			Vector nv = v[i].add(cursor);
			if (nv.y < 0) {
				if (nv.y == -1) {
					cursor = new Vector(cursor.x, cursor.y+1);
				} else {
					cursor = new Vector(cursor.x, cursor.y+2);
				}
				 
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
		for (Vector co : activePiece.getOffsets()) {
			int x = co.x + cursor.x;
			int y = co.y + cursor.y;
			grid[x][y] = p;
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
		for (Vector v : activePiece.getOffsets()) {
			Vector nv = v.add(cursor);
			Vector below = nv.add(new Vector(0,1));
			if (nv.y == size.height -1 ||
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
	 * Set the value of whether the game is running or not.
	 * 
	 * @param value whether the game is running
	 */
	public void setRunning(boolean value) {
		running = value;
	}
}
