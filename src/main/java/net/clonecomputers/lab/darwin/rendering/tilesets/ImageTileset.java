package net.clonecomputers.lab.darwin.rendering.tilesets;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

import net.clonecomputers.lab.darwin.rendering.*;
import net.clonecomputers.lab.darwin.world.entity.*;
import net.clonecomputers.lab.darwin.world.terrain.*;


public class ImageTileset extends AbstractTileset {
	
	// later this will be able to change
	private int tileWidth;
	private int tileHeight;
	
	private BufferedImage tileset;
	private float zoom = 1;
	
	// This constructor should probably change to allow proper map generation
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

	@Override
	public void drawTerrain(Terrain t, Graphics g) {
		drawTile(new ImageTile((int) (Math.random()*3)*2 + 10, 2), g);
	}
	
	public void drawTile(ImageTile t, Graphics g) {
		BufferedImage coloredTileset = t.getColorFilter().filter(tileset, null);
		g.drawImage(coloredTileset,
				0, 0,
				(int) (tileWidth*zoom), (int) (tileHeight*zoom),
				t.getTileX()*tileWidth, t.getTileY()*tileHeight,
				(t.getTileX()+1)*tileWidth, (t.getTileY()+1)*tileHeight,
			null);
	}

	@Override
	public void drawEntity(Entity e, Graphics g) {
		// don't draw it
	}
	
}
