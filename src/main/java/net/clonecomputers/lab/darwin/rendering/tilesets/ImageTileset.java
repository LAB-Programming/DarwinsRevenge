package net.clonecomputers.lab.darwin.rendering.tilesets;

import net.clonecomputers.lab.darwin.rendering.*;
import net.clonecomputers.lab.darwin.world.*;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.imageio.*;


public class ImageTileset extends AbstractTileset {
	
	private int tileWidth;
	private int tileHeight;
	
	private BufferedImage[] tiles;
	private float zoom = 1;
	
	public ImageTileset(Dimension tileSize, String... tilesetFiles)
			throws IOException, IllegalArgumentException {
		List<BufferedImage> tiles = new ArrayList<BufferedImage>(tilesetFiles.length * 256);
		for(String tilesetFile: tilesetFiles) {
			InputStream tilesetStream = this.getClass().getResourceAsStream(tilesetFile);
			if (tilesetStream == null) {
				throw new IllegalArgumentException(tilesetFile + " does not exist");
			}
			BufferedImage tileset = ImageIO.read(tilesetStream);
			for(int y = 0; y < (tileset.getHeight() / tileSize.height); y++) {
				for(int x = 0; x < (tileset.getWidth() / tileSize.width); x++) {
					BufferedImage tile = new BufferedImage(tileSize.width, tileSize.height, BufferedImage.TYPE_INT_RGB);
					Graphics g = tile.getGraphics();
					g.drawImage(tileset, 
							0, 0, tileSize.width, tileSize.height,
							x * tileSize.width, y * tileSize.height, (x+1) * tileSize.width, (y+1) * tileSize.height, null);
					g.dispose();
					tiles.add(tile);
				}
			}
		}
		this.tiles = tiles.toArray(new BufferedImage[0]);
		
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
		BufferedImage coloredTileset = colorFilter.filter(tiles[tileId], null);
		g.drawImage(coloredTileset,
				0, 0,
				(int) (tileWidth*zoom), (int) (tileHeight*zoom),
				0, 0,
				tileWidth, tileHeight,
			null);
	}
	
}
