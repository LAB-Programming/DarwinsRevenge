package net.clonecomputers.lab.darwin.rendering;

import java.awt.*;

import net.clonecomputers.lab.darwin.world.entity.*;
import net.clonecomputers.lab.darwin.world.entity.types.*;
import net.clonecomputers.lab.darwin.world.terrain.*;
import net.clonecomputers.lab.darwin.world.terrain.types.*;

public class SimpleTileset extends AbstractTileset {

	@Override
	public Dimension getTileDimensions() {
		return new Dimension(16, 16);
	}

	@Override
	public void drawTerrain(Terrain t, Graphics g) {
		System.out.println("drawing "+t);
		if(t.getClass().equals(Wall.class)) {
			g.setColor(Color.BLACK);
		} else {
			g.setColor(Color.LIGHT_GRAY);
		}
		g.fillRect(0, 0, 16, 16);
	}

	@Override
	public void drawEntity(Entity e, Graphics g) {
		if(e.getClass().equals(Rock.class)) {
			g.setColor(Color.DARK_GRAY);
			g.fillOval(0, 0, 16, 16);
		} else {
			g.setColor(Color.MAGENTA);
			g.drawOval(0, 0, 16, 16);
		}
	}

}
