package net.clonecomputers.lab.darwin.rendering;

import net.clonecomputers.lab.darwin.world.*;

import java.awt.*;

public abstract class AbstractTileset implements Tileset {
	
	@Override
	public void drawTile(Tile t, Graphics g) {
		drawWorldObject(t.getTopmostObject(), g);
	}
	
	public void drawWorldObject(WorldObject o, Graphics g) {
		drawTileImage(new TileImageProperties(o.getImageId(), o.getFgColor(), o.getBgColor()), g);
	}
}
