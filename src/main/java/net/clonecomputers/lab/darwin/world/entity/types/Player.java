package net.clonecomputers.lab.darwin.world.entity.types;

import net.clonecomputers.lab.darwin.world.entity.Entity;


public class Player extends Entity {
	
	static {
		registerType(0, Player.class);
	}
	
	@Override
	public int getImageId() {
		return 0x40;
	}
	
	@Override
	public int getFgColor() {
		return 0xDDDDDD;
	}
	
	@Override
	public int getBgColor() {
		return 0;
	}
	
}
