package net.clonecomputers.lab.darwin.map;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.clonecomputers.lab.darwin.tiles.Tile;


public class TileMap {
	
	// later this will be able to change
	private int tileWidth = 10;
	private int tileHeight = 12;
	
	private BufferedImage tileset;
	private Dimension size;
	private float zoom = 1;
	
	private Tile[][] map;
	
	// This constructor should probably change to allow proper map generation
	public TileMap(String tilesetFile, Dimension sizeInTiles)
			throws IOException, IllegalArgumentException
	{
		InputStream tilesetStream = this.getClass().getResourceAsStream(tilesetFile);
		if (tilesetStream == null) {
			throw new IllegalArgumentException(tilesetFile + " does not exist");
		}
		BufferedImage img = ImageIO.read(tilesetStream);
		tileset = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = tileset.getGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		size = sizeInTiles;
		map = new Tile[size.width][size.height];
		for (int y = 0; y < size.height; ++y) {
			for (int x = 0; x < size.width; ++x) {
					map[x][y] = new Tile();
			}
		}
	}
	
	public void update(long delta) {
		// TODO tell tiles to update
	}
	
	public void render(Graphics2D g) {
		for (int y = 0; y < size.height; ++y) {
			for (int x = 0; x < size.width; ++x) {
				// Might be more efficient to have a destination image instead of creating
				// a new BufferedImage for every tile.
				BufferedImage coloredTileset = map[x][y].getColorFilter().filter(tileset, null);
				g.drawImage(coloredTileset,
						(int) (x*tileWidth*zoom), (int) (y*tileHeight*zoom),
						(int) ((x+1)*tileWidth*zoom), (int) ((y+1)*tileHeight*zoom),
						map[x][y].getTileX()*tileWidth, map[x][y].getTileY()*tileHeight,
						(map[x][y].getTileX()+1)*tileWidth, (map[x][y].getTileY()+1)*tileHeight,
					null);
			}
		}
	}
	
}
