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
	void updateGrid(Piece[][] grid);

	/** 
	 * update the score of the game.
	 * 
	 * @param score the new score.
	 */
	void updateScore(int score);
	
	/**
	 * Update the game to a game over state
	 */
	void gameOver();

	/**
	 * Level up
	 */
	void levelUp(int newLevel);
}
