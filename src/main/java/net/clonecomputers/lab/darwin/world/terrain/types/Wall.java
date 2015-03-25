package net.clonecomputers.lab.darwin.world.terrain.types;

import net.clonecomputers.lab.darwin.world.terrain.*;

public class Wall extends Terrain {
	static {
		registerType(1, Wall.class);
	}
	
	@Override
	public int getImageId() {
		return 0xb1;
	}
}
