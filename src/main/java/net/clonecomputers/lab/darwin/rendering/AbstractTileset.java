package net.clonecomputers.lab.darwin.rendering;

import net.clonecomputers.lab.darwin.world.*;

import java.awt.*;

public abstract class AbstractTileset implements Tileset {
	
	@Override
	public void drawTileImage(Tile t, Graphics g) {
		drawWorldObject(t.getTopmostObject(), g);
	}
	
	public abstract void drawWorldObject(WorldObject o, Graphics g);
}
