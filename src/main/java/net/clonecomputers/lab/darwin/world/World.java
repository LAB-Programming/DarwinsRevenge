package net.clonecomputers.lab.darwin.world;

import java.util.*;

import net.clonecomputers.lab.darwin.world.generate.*;

import com.google.common.collect.*;

public class World {
	private final WorldGenerator generator;
	private final List<Level> levels = Lists.newArrayList();
	private Level activeLevel;
	private int activeLevelNumber;
	
	public World(WorldGenerator generator) {
		this.generator = generator;
	}
	
	public Level getLevel() {
		return activeLevel;
	}
	
	public Level getLevel(int levelNumber) {
		Level level = levels.get(levelNumber);
		if(level == null) {
			level = new Level(generator.getGenerator(levelNumber), levelNumber);
		}
		return level;
	}
	
	public int getLevelNumber() {
		return activeLevelNumber;
	}
	
	public void setLevelNumber(int newLevelNumber) {
		activeLevelNumber = newLevelNumber;
		activeLevel = levels.get(newLevelNumber);
	}
	
	public void goUp() {
		setLevelNumber(activeLevelNumber - 1);
	}
	
	public void goDown() {
		setLevelNumber(activeLevelNumber + 1);
	}
}
