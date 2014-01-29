package ui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class BlockImageHandler {
	private HashMap<Color, BufferedImage> blocks;
	
	/**
	 * Make a new BlockImageHandler
	 * 
	 * @param img the block image
	 */
	public BlockImageHandler(File img) {
		BufferedImage block = null;
		try {
			block = ImageIO.read(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		blocks = new HashMap<Color, BufferedImage>();
		blocks.put(Color.BLUE, colourBlock(block, Color.BLUE));
		blocks.put(Color.GRAY, colourBlock(block, Color.GRAY));
		blocks.put(Color.RED, colourBlock(block, Color.RED));
		blocks.put(Color.GREEN, colourBlock(block, Color.GREEN));
		blocks.put(Color.CYAN, colourBlock(block, Color.CYAN));
		blocks.put(Color.MAGENTA, colourBlock(block, Color.MAGENTA));
		blocks.put(Color.YELLOW, colourBlock(block, Color.YELLOW));
	}
	
	public BufferedImage getColouredBlock(Color c) {
		return blocks.get(c);
	}
	
	private BufferedImage colourBlock(BufferedImage img, Color c) {
		BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		for (int x=0; x<img.getWidth(); x++) {
			for (int y=0; y<img.getHeight(); y++) {
				Color p = new Color(img.getRGB(x, y));
				int bright = brightness(p);
				int r = c.getRed() * bright / 255;
				int g = c.getGreen() * bright / 255;
				int b = c.getBlue() * bright / 255;
				Color newC = new Color(r,g,b);
				result.setRGB(x, y, newC.getRGB());
			}
		}	
		
		return result;
	}
	
	private int brightness(Color c) {
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		return (r+g+b)/3;
	}

}
