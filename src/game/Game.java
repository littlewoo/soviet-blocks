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
	private final Coords centre;
	
	private Piece[][] grid;
	private UI ui;
	private Timer timer;
	
	private Coords cursor;
	private Piece activePiece;
	
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
		centre = new Coords(size.width / 2, 0);
		
		grid = new Piece[size.width][size.height];
		ui.updateGrid(grid);
		gameLoop();
	}
	
	/**
	 * The game's main loop
	 */
	private void gameLoop()
	{
		newPiece();
		while (true) {
			ui.updateGrid(grid);
			tick();
			if (atBottom()) {
				deletePiece();
				newPiece();
			}
		}
	}
	
	/**
	 * Wait for a tick, then drop the cursor 1 block.
	 */
	private void tick() {
		timer.awaitNextTick();
		deletePiece();
		cursor = new Coords(cursor.x, cursor.y + 1);
		placePiece();
	}

	/**
	 * Place the cursor at the top middle, and place a new piece there.
	 */
	private void newPiece()
	{
		cursor = centre;
		activePiece = new Piece();
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
		for (Coords co : activePiece.getOffsets()) {
			int x = co.x + cursor.x;
			int y = co.y + cursor.y;
			grid[x][y] = p;
		}
	}
	
	/**
	 * Check whether the cursor is on the bottom row of the grid.
	 * 
	 * @return true if it is at the bottom
	 */
	private boolean atBottom()
	{
		return cursor.y >= size.height-2; // TODO: -2 should be -1, when proper
										  // settling is implemented
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
}
