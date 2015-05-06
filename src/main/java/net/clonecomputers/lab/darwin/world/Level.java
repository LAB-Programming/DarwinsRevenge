package net.clonecomputers.lab.darwin.world;

import net.clonecomputers.lab.darwin.world.entity.Entity;
import net.clonecomputers.lab.darwin.world.generate.*;

import com.google.common.collect.*;

public class Level {
	private final Table<Integer, Integer, Tile> tiles = HashBasedTable.create();
	private final LevelGenerator generator;
	private final int levelNum;
	
	public Level(LevelGenerator generator, int levelNum) {
		this.generator = generator;
		this.levelNum = levelNum;
	}
	
	public Tile getTile(int x, int y) {
		Tile t = tiles.get(x, y);
		if(t == null) {
			t = generator.generateTile(this, x, y);
			tiles.put(x, y, t);
		}
		return t;
	}
	
	public boolean moveEntityTo(Entity e, int x, int y, boolean checkIfPassable) {
		Tile dest = getTile(x, y);
		if(checkIfPassable && !dest.isPassable()) return false;
		getTile(e.getX(), e.getY()).removeEntity(e);
		dest.addEntity(e);
		e.setTile(dest);
		return true;
	}
	
	public boolean moveEntity(Entity e, int dx, int dy, boolean checkIfPassable) {
		return moveEntityTo(e, e.getX() + dx, e.getY() + dy, checkIfPassable);
	}
}
