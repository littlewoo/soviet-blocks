package ui;

import game.Piece;
import game.Vector;

import java.awt.Dimension;

public class PreviewPanel extends GameAreaPanel {

	private Piece nextPiece;
	
	public PreviewPanel() {
		super(new Dimension(5,5));
	}
	
	public void updateNextPiece(Piece p) {
		nextPiece = p;
		Vector[] vs = p.getOffsets();
		Piece[][] grid = new Piece[5][5];
		for (Vector v : vs) {
			grid[v.x+2][v.y+2] = p; // +2 to place in centre of grid  
		}
		update(grid);
		repaint();
	}
}
