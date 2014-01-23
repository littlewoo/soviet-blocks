package ui;

import game.Game;
import game.Piece;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A graphical user interface.
 * 
 * @author littlewoo
 *
 */
public class GUI implements UI {
	
	private GameAreaPanel gamePanel;
	private JFrame frame;
	
	/**
	 * Builds a new GUI.
	 */
	public GUI() {
		buildGUI();
		new Game(this);
	}
	
	/**
	 * Update the user interface with an updated grid.
	 * 
	 * @param grid the grid. Assumed to be a 10x25 grid.
	 */
	@Override
	public void updateGrid(Piece[][] grid) {
		gamePanel.update(grid);
	}
	
	/**
	 * Create the User Interface.
	 */
	private void buildGUI()
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		gamePanel = new GameAreaPanel();
		frame.getContentPane().add(gamePanel);
		frame.pack();
	}

	public static void main(String[] args) {
		new GUI();
	}
}
