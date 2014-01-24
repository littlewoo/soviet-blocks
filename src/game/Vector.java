package game;

/**
 * A coordinate pair.
 * 
 * @author littlewoo
 *
 */
public class Vector {
	public final int x, y;
	
	/**
	 * Create a new coordinate pair.
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector add(Vector a) {
		return new Vector(x + a.x, y + a.y);
	}
	
}
