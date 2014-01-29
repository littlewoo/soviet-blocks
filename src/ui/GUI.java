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
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.SystemColor;

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
	private JMenuBar menuBar;
	private JMenu mnGame;
	private JMenu mnHelp;
	private JMenuItem mntmAbout;
	private JMenuItem mntmHowToPlay;
	private JMenuItem mntmNewGame;
	private JMenuItem mntmQuit;
	private JMenuItem mntmHighScores;
	
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
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(new BorderLayout(2, 2));
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		gamePanel = new GameAreaPanel(size);
		gamePanel.setBackground(SystemColor.menu);
		gamePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		gamePanel.setPreferredSize(new Dimension(300, 775));
		gamePanel.setMinimumSize(new Dimension(10, 10));
		panel.add(gamePanel, BorderLayout.CENTER);
		
		JPanel infoPanel = new JPanel();
		panel.add(infoPanel, BorderLayout.EAST);
		GridBagLayout gbl_infoPanel = new GridBagLayout();
		gbl_infoPanel.columnWidths = new int[] {150};
		gbl_infoPanel.rowHeights = new int[] {150, 30, 75, 75, 300};
		gbl_infoPanel.columnWeights = new double[]{0.0};
		gbl_infoPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		infoPanel.setLayout(gbl_infoPanel);
		previewPanel = new PreviewPanel();
		previewPanel.setMaximumSize(new Dimension(500, 500));
		previewPanel.setBackground(Color.BLACK);
		previewPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		previewPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		previewPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		GridBagConstraints gbc_previewPanel = new GridBagConstraints();
		gbc_previewPanel.insets = new Insets(0, 0, 5, 0);
		gbc_previewPanel.anchor = GridBagConstraints.WEST;
		gbc_previewPanel.fill = GridBagConstraints.VERTICAL;
		gbc_previewPanel.gridx = 0;
		gbc_previewPanel.gridy = 0;
		infoPanel.add(previewPanel, gbc_previewPanel);
		scorePanel = new NumericalInfoPanel("Score");
		BorderLayout borderLayout_1 = (BorderLayout) scorePanel.getLayout();
		scorePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_scorePanel = new GridBagConstraints();
		gbc_scorePanel.insets = new Insets(0, 0, 5, 0);
		gbc_scorePanel.fill = GridBagConstraints.BOTH;
		gbc_scorePanel.gridx = 0;
		gbc_scorePanel.gridy = 2;
		infoPanel.add(scorePanel, gbc_scorePanel);
		levelPanel = new NumericalInfoPanel("Level");
		BorderLayout borderLayout = (BorderLayout) levelPanel.getLayout();
		GridBagConstraints gbc_levelPanel = new GridBagConstraints();
		gbc_levelPanel.insets = new Insets(0, 0, 5, 0);
		gbc_levelPanel.fill = GridBagConstraints.BOTH;
		gbc_levelPanel.gridx = 0;
		gbc_levelPanel.gridy = 3;
		infoPanel.add(levelPanel, gbc_levelPanel);
				
		frame.pack();
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnGame = new JMenu("Game");
		menuBar.add(mnGame);
		
		mntmNewGame = new JMenuItem("New Game");
		mnGame.add(mntmNewGame);
		
		mntmHighScores = new JMenuItem("High Scores");
		mnGame.add(mntmHighScores);
		
		mntmQuit = new JMenuItem("Quit");
		mnGame.add(mntmQuit);
		
		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmHowToPlay = new JMenuItem("How to play");
		mnHelp.add(mntmHowToPlay);
		
		mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
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
