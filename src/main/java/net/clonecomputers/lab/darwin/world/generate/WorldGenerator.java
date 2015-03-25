package net.clonecomputers.lab.darwin.world.generate;

public interface WorldGenerator {
	public LevelGenerator getGenerator(int levelNumber);
}
