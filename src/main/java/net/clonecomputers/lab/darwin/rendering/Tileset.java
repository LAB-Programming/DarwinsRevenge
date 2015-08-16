package net.clonecomputers.lab.darwin.rendering;

import java.awt.*;

import net.clonecomputers.lab.darwin.world.*;

public interface Tileset {

	public void drawTileImage(TileImageProperties t, Graphics g);
	public void drawTile(Tile t, Graphics g);

	public Dimension getTileDimensions();
}
