package net.clonecomputers.lab.darwin.rendering;

import java.awt.*;

import net.clonecomputers.lab.darwin.world.*;

public interface Tileset {

	public void drawTileImage(int tileId, int fgColor, int bgColor, Graphics g);
	public void drawTile(Tile t, Graphics g);

	public Dimension getTileDimensions();
}
