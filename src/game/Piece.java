package game;

/**
 * A 4-block piece.
 * 
 * @author littlewoo
 */
public class Piece {
	
	public Vector[] getOffsets()
	{
		return new Vector[]{new Vector(0,0),
							new Vector(-1,0),
							new Vector(1,0),
							new Vector(0,1)};
	}
}
