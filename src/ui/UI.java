package ui;
import game.Piece;

/**
 * The user interface.
 * 
 * @author littlewoo
 *
 */
public interface UI {
	
	/**
	 * Update the user interface to reflect a changed game state.
	 * 
	 * @param grid the game area, a 10x25 grid containing Pieces
	 */
	public void updateGrid(Piece[][] grid);

}
