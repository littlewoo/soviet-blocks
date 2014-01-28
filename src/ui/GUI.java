package ui;

import game.Game;
import game.Piece;
import game.Vector;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import java.awt.FlowLayout;

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
	private PreviewPanel previewPanel;
	private NumericalInfoPanel scorePanel;
	private NumericalInfoPanel levelPanel;
	
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
	 * Update the score.
	 * 
	 * @param score the new score.
	 */
	@Override
	public void updateScore(int score) {
		scorePanel.updateValue(String.format("%08d", score));
	}
	
	/**
	 * Update the next piece preview.
	 * 
	 * @param piece the next piece
	 */
	@Override
	public void updateNextPiece(Piece p) {
		previewPanel.updateNextPiece(p);
	}
	
	/**
	 * Respond to a game over state
	 */
	@Override
	public void gameOver() {
		System.out.println("GAME OVER! Press (" + 
							KeyEvent.getKeyText(newGameKey) + 
							") to start a new game.");
	}
	
	/**
	 * Create the User Interface.
	 */
	private void buildGUI()
	{
		gamePanel = new GameAreaPanel(size);
		gamePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		FlowLayout flowLayout = (FlowLayout) gamePanel.getLayout();
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		scorePanel = new NumericalInfoPanel("Score");
		scorePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		previewPanel = new PreviewPanel();
		previewPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		levelPanel = new NumericalInfoPanel("Level");
		previewPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		infoPanel.add(scorePanel, BorderLayout.NORTH);
		infoPanel.add(previewPanel, BorderLayout.CENTER);
		infoPanel.add(levelPanel, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		panel.add(gamePanel, BorderLayout.CENTER);
		panel.add(infoPanel, BorderLayout.EAST);
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
				
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
			updateScore(0);
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

	/**
	 * Level up
	 * 
	 * @param newLevel the new level.
	 */
	@Override
	public void levelUp(int newLevel) {
		levelPanel.updateValue("" + newLevel);
	}


}
