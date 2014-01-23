package game;

import ui.UI;

/**
 * The game. Controls the game grid, and the rest of the game.
 * 
 * @author littlewoo
 *
 */
public class Game {
	private Piece[][] grid;
	private UI ui;
	
	/**
	 * Creates a new game. For now, this is just a grid with Pieces for 
	 * demonstration/testing.
	 * 
	 * @param ui the user interface which will display this game.
	 */
	public Game(UI ui) {
		this.ui = ui;
		
		grid = new Piece[10][25];
		
		Piece p = new Piece();
		grid[4][10] = p;
		grid[3][10] = p;
		grid[5][10] = p;
		grid[4][9] = p;
		grid[0][24] = p;
		grid[1][24] = p;
		grid[2][24] = p;
		grid[3][24] = p;
		grid[6][24] = p;
		grid[7][24] = p;
		grid[8][24] = p;
		grid[8][23] = p;
		
		
		this.ui.updateGrid(grid);
	}
}
