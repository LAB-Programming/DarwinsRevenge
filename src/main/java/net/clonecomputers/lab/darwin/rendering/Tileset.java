package net.clonecomputers.lab.darwin.rendering;

import java.awt.*;

import net.clonecomputers.lab.darwin.world.*;

public interface Tileset {
	public void drawTileImage(Tile t, Graphics g);

	public Dimension getTileDimensions();
}
