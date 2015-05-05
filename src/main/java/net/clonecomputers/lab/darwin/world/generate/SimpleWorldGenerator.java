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
		public Tile generateTile(Level l, int x, int y) {
			Terrain t;
			if((x == 0 && y == 0) || r.nextInt(levelNumber + 14) < 10) {
				t = new Floor();
			} else {
				t = new Wall();
			}
			if(t instanceof Floor && r.nextFloat() > .9) {
				return new Tile(l, x, y, t, new Rock());
			}
			return new Tile(l, x, y, t);
		}
		
	}
}
