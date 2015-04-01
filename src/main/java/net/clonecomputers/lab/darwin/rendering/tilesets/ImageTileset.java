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
	
	private BufferedImage[] tilesets;
	private float zoom = 1;
	
	public ImageTileset(Dimension tileSize, String... tilesetFiles)
			throws IOException, IllegalArgumentException {
		tilesets = new BufferedImage[tilesetFiles.length];
		for(int i = 0; i < tilesetFiles.length; i++) {
			InputStream tilesetStream = this.getClass().getResourceAsStream(tilesetFiles[i]);
			if (tilesetStream == null) {
				throw new IllegalArgumentException(tilesetFiles[i] + " does not exist");
			}
			BufferedImage img = ImageIO.read(tilesetStream);
			tilesets[i] = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics g = tilesets[i].getGraphics();
			g.drawImage(img, 0, 0, null);
			g.dispose();
		}
		
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
		drawTile(tileId, createColorFilter(), g);
	}
	
	public void drawTile(int tileId, BufferedImageOp colorFilter, Graphics g) {
		int x = tileId % 16, y = (tileId / 16) % 16, z = tileId / 256;
		BufferedImage coloredTileset = colorFilter.filter(tilesets[z], null);
		g.drawImage(coloredTileset,
				0, 0,
				(int) (tileWidth*zoom), (int) (tileHeight*zoom),
				x*tileWidth, y*tileHeight,
				(x+1)*tileWidth, (y+1)*tileHeight,
			null);
	}
	
}
