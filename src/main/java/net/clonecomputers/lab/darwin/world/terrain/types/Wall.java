package net.clonecomputers.lab.darwin.world.terrain.types;

import net.clonecomputers.lab.darwin.world.terrain.*;

public class Wall extends Terrain {
	static {
		registerType(1, Wall.class);
	}
	
	@Override
	public int getImageId() {
		int id = 0x100;
		if(isWall(1,0)) id |= 0x04;
		if(isWall(-1,0)) id |= 0x01;
		if(isWall(0,1)) id |= 0x02;
		if(isWall(0,-1)) id |= 0x08;
		return id;
	}
	
	private boolean isWall(int dx, int dy) {
		return tile.level.getTile(tile.x + dx, tile.y + dy).getTerrain() instanceof Wall;
	}

	@Override
	public int getFgColor() {
		return 0x00aaaaaa;
	}

	@Override
	public int getBgColor() {
		return 0;
	}

	@Override
	public boolean isPassable() {
		return false;
	}
}
