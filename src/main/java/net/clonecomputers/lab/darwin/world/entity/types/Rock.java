package net.clonecomputers.lab.darwin.world.entity.types;

import net.clonecomputers.lab.darwin.world.entity.*;

public class Rock extends Entity {
	static {
		registerType(1, Rock.class);
	}
	
	@Override
	public int getImageId() {
		return 0x0f;
	}

	@Override
	public int getFgColor() {
		return 0x00222222;
	}

	@Override
	public int getBgColor() {
		return 0;
	}
}
