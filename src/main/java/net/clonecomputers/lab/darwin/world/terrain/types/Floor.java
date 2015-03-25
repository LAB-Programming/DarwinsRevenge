package net.clonecomputers.lab.darwin.world.terrain.types;

import net.clonecomputers.lab.darwin.world.*;
import net.clonecomputers.lab.darwin.world.terrain.*;

public class Floor implements Terrain {
	static {
		ObjectManager.getTerrainManager().registerType(Floor.class);
	}
}
