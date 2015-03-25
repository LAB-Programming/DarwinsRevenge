package net.clonecomputers.lab.darwin.rendering;

import java.awt.*;

import net.clonecomputers.lab.darwin.world.*;
import net.clonecomputers.lab.darwin.world.entity.*;
import net.clonecomputers.lab.darwin.world.terrain.*;

public abstract class AbstractTileset implements Tileset {
	
	@Override
	public void drawTileImage(Tile t, Graphics g) {
		System.out.println("Drawing "+t+" at "+g.getClipBounds());
		drawTerrain(t.getTerrain(), g);
		for(Entity e: t.getEntities()) {
			drawEntity(e, g);
		}
	}
	
	public abstract void drawTerrain(Terrain t, Graphics g);
	public abstract void drawEntity(Entity e, Graphics g);
}
