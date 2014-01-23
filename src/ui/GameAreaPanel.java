package ui;

import game.Coords;
import game.Piece;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GameAreaPanel extends JPanel {
	private static int GRID_WIDTH = 10;
	private static int GRID_HEIGHT = 25;
	private static int BLOCK_SIZE = 30;
	private static int IMG_WIDTH = GRID_WIDTH * BLOCK_SIZE;
	private static int IMG_HEIGHT = GRID_HEIGHT * BLOCK_SIZE;
	
	private Piece[][] grid;
	
	public GameAreaPanel() {
		setPreferredSize(new Dimension(IMG_WIDTH, IMG_HEIGHT));
		
	}
	
	public void update(Piece[][] newGrid) {
		grid = newGrid;
		paint(getGraphics());
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        draw(g2);
    }
	
	private void draw(Graphics2D g) {
		for (int x=0; x<GRID_WIDTH; x++) {
			for (int y=0; y<GRID_HEIGHT; y++) {
				Color c;
				if (grid[x][y] == null) {
					c = Color.BLACK;
				} else {
					c = Color.WHITE;
				}
				drawSquare(new Coords(x,y), c, g);
			}
		}
	}
	
	private void drawSquare(Coords loc, Color colour, Graphics2D g) {
		int x = loc.x * BLOCK_SIZE;
		int y = loc.y * BLOCK_SIZE;
		g.setColor(colour);
		g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
	}
}
