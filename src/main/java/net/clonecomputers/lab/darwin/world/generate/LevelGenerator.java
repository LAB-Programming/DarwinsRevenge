package net.clonecomputers.lab.darwin.world.generate;

import net.clonecomputers.lab.darwin.world.*;

public interface LevelGenerator {
	public Tile generateTile(int x, int y);
}
