package ui;

import game.Game;
import game.Piece;
import game.Vector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
	private final static Dimension size = new Dimension(10, 25); 
	private static final int leftKey = KeyEvent.VK_LEFT;
	private static final int rightKey = KeyEvent.VK_RIGHT;
	private static final int downKey = KeyEvent.VK_DOWN;
	private static final int newGameKey = KeyEvent.VK_N;
	private static final int dropKey = KeyEvent.VK_SPACE;
	private static final int rotateKey = KeyEvent.VK_UP;
	
	private GameAreaPanel gamePanel;
	private Game game;
	private JFrame frame;
	
	/**
	 * Builds a new GUI.
	 */
	public GUI() {
		buildGUI();
		game = new Game(this, size);
		frame.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				keyPress(key);
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
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
		gamePanel = new GameAreaPanel(size);
		frame.getContentPane().add(gamePanel);
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Respond to key press.
	 * @param key the key pressed
	 */
	private void keyPress(int key) {
		Vector moveOffset = null;
		if (key == leftKey) {
			moveOffset = new Vector(-1,0);
		} else if (key == rightKey) {
			moveOffset = new Vector(1,0);
		} else if (key == downKey) {
			moveOffset = new Vector(0,1);
		} else if (key == newGameKey) {
			game.setRunning(false);
			game.clearGrid();
			game = new Game(this, size);
		} else if (key == dropKey) {
			game.drop();
		} else if (key == rotateKey) {
			game.rotate();
		}
		if (moveOffset != null) {
			game.movePiece(moveOffset);
		}
	}

	public static void main(String[] args) {
		new GUI();
	}
}
