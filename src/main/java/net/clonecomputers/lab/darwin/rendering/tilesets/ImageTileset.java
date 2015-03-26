package net.clonecomputers.lab.darwin.rendering.tilesets;

import net.clonecomputers.lab.darwin.rendering.*;
import net.clonecomputers.lab.darwin.world.*;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Arrays;

import javax.imageio.*;


public class ImageTileset extends AbstractTileset {
	
	private int tileWidth;
	private int tileHeight;
	
	private BufferedImage tileset;
	private float zoom = 1;
	
	public ImageTileset(String tilesetFile, Dimension tileSize)
			throws IOException, IllegalArgumentException {
		InputStream tilesetStream = this.getClass().getResourceAsStream(tilesetFile);
		if (tilesetStream == null) {
			throw new IllegalArgumentException(tilesetFile + " does not exist");
		}
		BufferedImage img = ImageIO.read(tilesetStream);
		tileset = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = tileset.getGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		
		tileWidth = tileSize.width;
		tileHeight = tileSize.height;
	}

	@Override
	public Dimension getTileDimensions() {
		return new Dimension(tileWidth, tileHeight);
	}
	
	private BufferedImageOp createColorFilter() {
		byte gray = (byte) ((int) (Math.random()*2)*64 + 127); //either 127 or 191
		byte[] r = new byte[256];
		byte[] g = new byte[256];
		byte[] b = new byte[256];
		Arrays.fill(r, (byte) gray);
		Arrays.fill(g, (byte) gray);
		Arrays.fill(b, (byte) gray);
		r[0] = 0; g[0] = 0; b[0] = 0;
		return new LookupOp(new ByteLookupTable(0, new byte[][]{r, g, b}), null);
	}

	@Override
	public void drawWorldObject(WorldObject o, Graphics g) {
		int tileId = o.getImageId();
		drawTile(tileId % 16, tileId / 16, createColorFilter(), g);
	}
	
	public void drawTile(int x, int y, BufferedImageOp colorFilter, Graphics g) {
		BufferedImage coloredTileset = colorFilter.filter(tileset, null);
		g.drawImage(coloredTileset,
				0, 0,
				(int) (tileWidth*zoom), (int) (tileHeight*zoom),
				x*tileWidth, y*tileHeight,
				(x+1)*tileWidth, (y+1)*tileHeight,
			null);
	}
	
}
