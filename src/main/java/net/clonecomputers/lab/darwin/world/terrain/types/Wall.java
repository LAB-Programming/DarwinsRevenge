package net.clonecomputers.lab.darwin.world.terrain.types;

import net.clonecomputers.lab.darwin.world.terrain.*;

public class Wall extends Terrain {
	static {
		registerType(1, Wall.class);
	}
	
	@Override
	public int getImageId() {
		int numConnectedNeighbors = 0;
		if(isWall(1,0)) numConnectedNeighbors++;
		if(isWall(-1,0)) numConnectedNeighbors++;
		if(isWall(0,1)) numConnectedNeighbors++;
		if(isWall(0,-1)) numConnectedNeighbors++;
		if(numConnectedNeighbors == 0) return 0xfe;
		if(numConnectedNeighbors == 1) {
			if(isWall(1,0) || isWall(-1,0)) return 0xcd;
			if(isWall(0,1) || isWall(0,-1)) return 0xba;
		}
		if(numConnectedNeighbors == 2) {
			if(isWall(1,0) && isWall(0,1)) return 0xc8;
			if(isWall(1,0) && isWall(0,-1)) return 0xc9;
			if(isWall(1,0) && isWall(-1,0)) return 0xcd;
			if(isWall(-1,0) && isWall(0,1)) return 0xbc;
			if(isWall(-1,0) && isWall(0,-1)) return 0xbb;
			if(isWall(0,1) && isWall(0,-1)) return 0xba;
		}
		if(numConnectedNeighbors == 3) {
			if(!isWall(1,0)) return 0xb9;
			if(!isWall(-1,0)) return 0xcc;
			if(!isWall(0,1)) return 0xcb;
			if(!isWall(0,-1)) return 0xca;
		}
		if(numConnectedNeighbors == 4) return 0xce;
		return 0xb1;
	}
	
	private boolean isWall(int dx, int dy) {
		return tile.level.getTile(tile.x + dx, tile.y + dy).getTerrain() instanceof Wall;
	}
}
