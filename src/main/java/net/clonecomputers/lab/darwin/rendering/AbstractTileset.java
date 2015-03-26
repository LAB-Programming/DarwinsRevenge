package net.clonecomputers.lab.darwin.rendering;

import net.clonecomputers.lab.darwin.world.*;
import net.clonecomputers.lab.darwin.world.entity.*;

import java.awt.*;

public abstract class AbstractTileset implements Tileset {
	
	@Override
	public void drawTileImage(Tile t, Graphics g) {
		drawWorldObject(t.getTerrain(), g);
		for(Entity e: t.getEntities()) {
			drawWorldObject(e, g);
		}
	}
	
	public abstract void drawWorldObject(WorldObject o, Graphics g);
}
