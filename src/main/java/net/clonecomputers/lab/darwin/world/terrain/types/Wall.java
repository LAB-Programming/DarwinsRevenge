package net.clonecomputers.lab.darwin.world.terrain.types;

import net.clonecomputers.lab.darwin.world.*;
import net.clonecomputers.lab.darwin.world.terrain.*;

public class Wall implements Terrain {
	static {
		ObjectManager.getTerrainManager().registerType(Wall.class);
	}
}
