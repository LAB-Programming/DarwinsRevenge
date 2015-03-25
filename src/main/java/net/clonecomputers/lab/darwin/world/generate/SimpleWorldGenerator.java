package net.clonecomputers.lab.darwin.world.generate;

import java.util.*;

import net.clonecomputers.lab.darwin.world.*;
import net.clonecomputers.lab.darwin.world.entity.types.*;
import net.clonecomputers.lab.darwin.world.terrain.*;
import net.clonecomputers.lab.darwin.world.terrain.types.*;

public class SimpleWorldGenerator implements WorldGenerator {
	private final Random r = new Random();
	
	@Override
	public LevelGenerator getGenerator(int levelNumber) {
		return new SimpleLevelGenerator(levelNumber, r);
	}
	
	public class SimpleLevelGenerator implements LevelGenerator {
		private final int levelNumber;
		private final Random r;

		public SimpleLevelGenerator(int levelNumber, Random r) {
			this.levelNumber = levelNumber;
			this.r = r;
		}

		@Override
		public Tile generateTile(int x, int y) {
			Terrain t;
			if(r.nextInt(levelNumber + 5) < 10) {
				t = new Wall();
			} else {
				t = new Floor();
			}
			if(t instanceof Floor && r.nextFloat() > .9) {
				return new Tile(t, new Rock());
			}
			return new Tile(t);
		}
		
	}
}
