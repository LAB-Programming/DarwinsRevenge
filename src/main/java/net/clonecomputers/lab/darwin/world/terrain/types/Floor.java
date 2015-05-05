package net.clonecomputers.lab.darwin.world.terrain.types;

import net.clonecomputers.lab.darwin.world.terrain.*;

public class Floor extends Terrain {
	static {
		registerType(0, Floor.class);
	}
	
	@Override
	public int getImageId() {
		return 0x00;
	}

	@Override
	public int getFgColor() {
		return 0;
	}

	@Override
	public int getBgColor() {
		return 0;
	}

	@Override
	public boolean isPassable() {
		return true;
	}
}
