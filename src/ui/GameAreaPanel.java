package ui;

import game.Vector;
import game.Piece;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * Panel for displaying the game area. 
 * 
 * @author littlewoo
 */
public class GameAreaPanel extends JPanel {
	private final int gridWidth;
	private final int gridHeight;
	private static int BLOCK_SIZE = 30;
	private final int imgWidth;
	private final int imgHeight;
	
	protected Piece[][] grid;
	
	/**
	 * Create a new GameAreaPanel.
	 */
	public GameAreaPanel(Dimension size) {
		gridWidth = size.width;
		gridHeight = size.height;
		imgWidth = gridWidth * BLOCK_SIZE;
		imgHeight = gridHeight * BLOCK_SIZE;
		setPreferredSize(new Dimension(imgWidth, imgHeight));
	}
	
	/**
	 * Update this panel with a new grid.
	 * 
	 * @param newGrid the Piece grid to update this panel with.
	 */
	public void update(Piece[][] newGrid) {
		grid = newGrid;
		repaint();
	}
	
	/**
	 * Paint this component. Draw the grid as currently stored.
	 * 
	 */
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        draw(g2);
    }
	
	/**
	 * Draw this panel - draw the grid with the color of each path.
	 * 
	 * @param g the graphics to draw on.
	 */
	protected void draw(Graphics2D g) {
		for (int x=0; x<gridWidth; x++) {
			for (int y=0; y<gridHeight; y++) {
				Color c;
				if (grid == null || grid[x][y] == null) {
					c = Color.BLACK;
				} else {
					c = grid[x][y].getColour();
				}
				drawSquare(new Vector(x,y), c, g);
			}
		}
	}
	
	/**
	 * draw an individual square. 
	 * 
	 * @param loc the grid coordinates of the square to draw
	 * @param colour the colour of the square
	 * @param g the graphics to draw on
	 */
	private void drawSquare(Vector loc, Color colour, Graphics2D g) {
		int x = loc.x * BLOCK_SIZE;
		int y = loc.y * BLOCK_SIZE;
		g.setColor(colour);
		g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
	}
}
