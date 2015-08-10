package net.clonecomputers.lab.darwin.rendering;

import java.awt.*;

import net.clonecomputers.lab.darwin.world.*;

public interface Tileset {

	public void drawByImageId(int imageId, int fgColor, int bgColor, Graphics g);
	public void drawTileImage(Tile t, Graphics g);

	public Dimension getTileDimensions();
}
