package net.clonecomputers.lab.darwin.rendering.tilesets;

import net.clonecomputers.lab.darwin.rendering.*;
import net.clonecomputers.lab.darwin.world.*;
import net.clonecomputers.lab.darwin.world.entity.types.*;
import net.clonecomputers.lab.darwin.world.terrain.*;
import net.clonecomputers.lab.darwin.world.terrain.types.*;

import java.awt.*;

public class SimpleTileset extends AbstractTileset {

	@Override
	public Dimension getTileDimensions() {
		return new Dimension(16, 16);
	}

	@Override
	public void drawWorldObject(WorldObject o, Graphics g) {
		if(o instanceof Terrain) {
			if(o.getClass().equals(Wall.class)) {
				g.setColor(Color.BLACK);
			} else {
				g.setColor(Color.LIGHT_GRAY);
			}
			g.fillRect(0, 0, 16, 16);
		} else {
			if(o.getClass().equals(Rock.class)) {
				g.setColor(Color.DARK_GRAY);
				g.fillOval(0, 0, 16, 16);
			} else {
				g.setColor(Color.MAGENTA);
				g.drawOval(0, 0, 16, 16);
			}
		}
	}

}
