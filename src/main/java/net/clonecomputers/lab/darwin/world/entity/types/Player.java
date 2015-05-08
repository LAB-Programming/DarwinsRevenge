package net.clonecomputers.lab.darwin.world.entity.types;

import net.clonecomputers.lab.darwin.world.entity.Entity;


public class Player extends Entity {
	
	private int hitPoints = 10;
	
	static {
		registerType(0, Player.class);
	}
	
	public int getHP() {
		return hitPoints;
	}
	
	@Override
	public int getImageId() {
		return 0x40;
	}
	
	@Override
	public int getFgColor() {
		return 0x00ffbbff;
	}
	
	@Override
	public int getBgColor() {
		return 0;
	}
	
}
