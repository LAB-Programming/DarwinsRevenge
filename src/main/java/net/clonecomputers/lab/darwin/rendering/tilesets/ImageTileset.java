package net.clonecomputers.lab.darwin.rendering.tilesets;

import net.clonecomputers.lab.darwin.rendering.*;

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
	
	private HashMap<TileImageProperties, BufferedImage> tileCache = new HashMap<TileImageProperties, BufferedImage>();
	
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
	
	private BufferedImageOp createColorFilter(int fgColor, int bgColor) {
		byte[] r = new byte[256];
		byte[] g = new byte[256];
		byte[] b = new byte[256];
		Arrays.fill(r, (byte)((fgColor & 0x00ff0000) >> 16));
		Arrays.fill(g, (byte)((fgColor & 0x0000ff00) >> 8));
		Arrays.fill(b, (byte)((fgColor & 0x000000ff)));
		r[0] = (byte)((bgColor & 0x00ff0000) >> 16);
		g[0] = (byte)((bgColor & 0x0000ff00) >> 8);
		b[0] = (byte)((bgColor & 0x000000ff));
		return new LookupOp(new ByteLookupTable(0, new byte[][]{r, g, b}), null);
	}

	@Override
	public void drawTileImage(TileImageProperties t, Graphics g) {
		BufferedImage tileImage = tileCache.get(t);
		if(tileImage == null) {
			tileImage = filterTile(t.tileId, createColorFilter(t.fgColor, t.bgColor));
			tileCache.put(t, tileImage);
		}
		drawImage(tileImage, g);
	}
	
	public BufferedImage filterTile(int tileId, BufferedImageOp colorFilter) {
		return colorFilter.filter(tiles[tileId], null);
	}
	
	public void drawImage(BufferedImage coloredTile, Graphics g) {
		g.drawImage(coloredTile,
				0, 0,
				(int) (tileWidth*zoom), (int) (tileHeight*zoom),
				0, 0,
				tileWidth, tileHeight,
			null);
	}
	
}
