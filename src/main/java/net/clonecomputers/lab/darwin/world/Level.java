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
	
	public void moveEntity(Entity e, int x, int y, int dx, int dy) throws IllegalArgumentException, RuntimeException {
		Tile originTile = getTile(x, y);
		Tile destTile = getTile(x + dx, y + dy);
		if (!originTile.removeEntity(e)) {
			throw new IllegalArgumentException("Cannot find Entity " + e + " in tile (" + x + "," + y + ") on level " + levelNum);
		}
		if (!destTile.addEntity(e)) {
			// Couldn't think of what exception this should be
			throw new RuntimeException("Cannot add Entity " + e + " to tile (" + x + "," + y + ") on level " + levelNum);
		}
		e.setTile(destTile);
	}
}
