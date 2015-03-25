package net.clonecomputers.lab.darwin.world;

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
}
