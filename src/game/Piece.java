package game;

/**
 * A 4-block piece.
 * 
 * @author littlewoo
 */
public class Piece {
	
	public Coords[] getOffsets()
	{
		return new Coords[]{new Coords(0,0),
							new Coords(-1,0),
							new Coords(1,0),
							new Coords(0,1)};
	}
}
