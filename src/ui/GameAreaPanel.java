package ui;

import game.Piece;
import game.Vector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
	
	private BufferedImage blockImage;
	private BlockImageHandler blockImageHandler;
	
	/**
	 * Create a new GameAreaPanel.
	 */
	public GameAreaPanel(Dimension size) {
		gridWidth = size.width;
		gridHeight = size.height;
		imgWidth = gridWidth * BLOCK_SIZE;
		imgHeight = gridHeight * BLOCK_SIZE;
		setPreferredSize(new Dimension(imgWidth, imgHeight));
		setMinimumSize(new Dimension(imgWidth, imgHeight));
		
		File f = new File("D:/Users/jdl/prog/soviet-blocks/assets/block.png");
		blockImageHandler = new BlockImageHandler(f);
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
				if (grid == null || grid[x][y] == null) {
					drawSquare(new Vector(x,y), Color.BLACK, g);
				} else {
					Color c = grid[x][y].getColour();
					drawBlock(new Vector(x,y), c, g);
				}
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
	
	/**
	 * Draw a block.
	 * @param loc the grid coordinates of the block
	 * @param g the graphics to draw on
	 */
	private void drawBlock(Vector loc, Color colour, Graphics2D g) {
		int x = loc.x * BLOCK_SIZE;
		int y = loc.y * BLOCK_SIZE;
		g.drawImage(blockImageHandler.getColouredBlock(colour), null, x, y);
	}
}
