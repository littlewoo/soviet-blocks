package game;

import java.awt.Color;

/**
 * A 4-block piece.
 * 
 * @author littlewoo
 */
public class Piece {
	private final PieceType type;
	private static RandomEnum<PieceType> rand = new RandomEnum<>(PieceType.class);
	
	/**
	 * Create a new piece
	 * @param t the type of this piece
	 */
	private Piece(PieceType t) {
		type = t;
	}
	
	/**
	 * 
	 * @return the offsets of this piece
	 */
	public Vector[] getOffsets()
	{
		return type.offsets;
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
		return new Piece(rand.getRandomValue()); 
	}
	
	/**
	 * the different types of piece.
	 *
	 */
	private enum PieceType {

		LONG(new Vector[]{
				new Vector(-1,0),new Vector(0,0),new Vector(1,0),new Vector(2,0)}, 
				Color.RED),
		ELL(new Vector[]{
				new Vector(-1,0),new Vector(0,0),new Vector(0,1),new Vector(0,2)}, 
				Color.YELLOW),
		PEE(new Vector[]{
				new Vector(0,0),new Vector(1,0),new Vector(0,1),new Vector(0,2)}, 
				Color.MAGENTA),
		TEE(new Vector[]{
				new Vector(-1,0),new Vector(0,0),new Vector(0,1),new Vector(1,0)}, 
				Color.GREEN),
		ESS(new Vector[]{
				new Vector(1,0),new Vector(0,0),new Vector(0,1),new Vector(-1,1)}, 
				Color.BLUE),
		ZED(new Vector[]{
				new Vector(-1,0),new Vector(0,0),new Vector(0,1),new Vector(1,1)}, 
				Color.CYAN),
		SQUARE(new Vector[]{
				new Vector(0,0),new Vector(0,1),new Vector(1,1),new Vector(1,0)}, 
				Color.GRAY);
		
		public final Color colour;
		public final Vector[] offsets;
		
		PieceType(Vector[] o, Color c) {
			offsets = o;
			colour = c;
		}
	}
}
