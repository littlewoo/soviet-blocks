package game;

import java.awt.Color;
import java.util.Random;

/**
 * A 4-block piece.
 * 
 * @author littlewoo
 */
public class Piece {
	private final PieceType type;
	private static RandomEnum<PieceType> randPiece = new RandomEnum<>(PieceType.class);
	private static Random randNo = new Random();
	
	private int rotation;
	
	/**
	 * Create a new piece
	 * @param t the type of this piece
	 */
	private Piece(PieceType t, int rot) {
		type = t;
		rotation = rot;
	}
	
	/**
	 * Rotate this piece through 90 degrees clockwise.
	 */
	public void rotate() {
		rotation = (rotation + 1) % 4;
	}
	
	/**
	 * Rotate this piece through 90 degrees anti-clockwise
	 */
	public void reverseRotate() {
		rotation = (rotation - 1) % 4;
	}
	
	/**
	 * 
	 * @return the offsets of this piece
	 */
	public Vector[] getOffsets()
	{
		return type.offsets[rotation];
	}
	
	/**
	 * 
	 * @return the colour of this piece
	 */
	public Color getColour()
	{
		return type.colour;
	}
	
	/**
	 * @return a piece of a random type
	 */
	public static Piece getRandomPiece()
	{
		return new Piece(randPiece.getRandomValue(), randNo.nextInt(4)); 
	}
	
	/**
	 * the different types of piece.
	 *
	 */
	private enum PieceType {

		LONG(new Vector[][]{
				{new Vector(-1,0),new Vector(0,0),new Vector(1,0),new Vector(2,0)},
				{new Vector(0,-1),new Vector(0,0),new Vector(0,1),new Vector(0,2)},
				{new Vector(-1,0),new Vector(0,0),new Vector(1,0),new Vector(2,0)},
				{new Vector(0,-1),new Vector(0,0),new Vector(0,1),new Vector(0,2)}
				}, Color.RED),
		ELL(new Vector[][]{
				{new Vector(0,0),new Vector(0,-1),new Vector(0,1),new Vector(1,1)},
				{new Vector(0,0),new Vector(-1,0),new Vector(1,0),new Vector(1,-1)},
				{new Vector(0,0),new Vector(0,1),new Vector(0,-1),new Vector(-1,-1)},
				{new Vector(0,0),new Vector(-1,0),new Vector(-1,1),new Vector(1,0)}
				}, Color.YELLOW),
		JAY(new Vector[][]{
				{new Vector(0,0),new Vector(0,-1),new Vector(0,1),new Vector(-1,1)}, 
				{new Vector(0,0),new Vector(-1,0),new Vector(1,0),new Vector(1,1)},
				{new Vector(0,0),new Vector(0,1),new Vector(0,-1),new Vector(1,-1)},
				{new Vector(0,0),new Vector(-1,0),new Vector(1,0),new Vector(-1,-1)}
				}, Color.MAGENTA),
		TEE(new Vector[][]{
				{new Vector(-1,0),new Vector(0,0),new Vector(0,-1),new Vector(1,0)}, 
				{new Vector(0,0),new Vector(0,-1),new Vector(1,0),new Vector(0,1)},
				{new Vector(0,0),new Vector(-1,0),new Vector(0,1),new Vector(1,0)},
				{new Vector(0,0),new Vector(0,1),new Vector(-1,0),new Vector(0,-1)}
				}, Color.GREEN),
		ESS(new Vector[][]{
				{new Vector(1,0),new Vector(0,0),new Vector(0,1),new Vector(-1,1)}, 
				{new Vector(0,0),new Vector(-1,-1),new Vector(-1,0),new Vector(0,1)},
				{new Vector(1,0),new Vector(0,0),new Vector(0,1),new Vector(-1,1)}, 
				{new Vector(0,0),new Vector(-1,-1),new Vector(-1,0),new Vector(0,1)}
				}, Color.BLUE),
		ZED(new Vector[][]{
				{new Vector(-1,0),new Vector(0,0),new Vector(0,1),new Vector(1,1)}, 
				{new Vector(0,0),new Vector(1,0),new Vector(1,-1),new Vector(0,1)},
				{new Vector(-1,0),new Vector(0,0),new Vector(0,1),new Vector(1,1)}, 
				{new Vector(0,0),new Vector(1,0),new Vector(1,-1),new Vector(0,1)}
				}, Color.CYAN),
		SQUARE(new Vector[][]{
				{new Vector(0,0),new Vector(0,1),new Vector(1,1),new Vector(1,0)}, 
				{new Vector(0,0),new Vector(0,1),new Vector(1,1),new Vector(1,0)}, 
				{new Vector(0,0),new Vector(0,1),new Vector(1,1),new Vector(1,0)}, 
				{new Vector(0,0),new Vector(0,1),new Vector(1,1),new Vector(1,0)}
				}, Color.GRAY);
		
		public final Color colour;
		public final Vector[][] offsets;
		
		PieceType(Vector[][] o, Color c) {
			offsets = o;
			colour = c;
		}
	}
}
