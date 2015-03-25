package net.clonecomputers.lab.darwin.world;

import java.util.*;

import net.clonecomputers.lab.darwin.world.entity.*;
import net.clonecomputers.lab.darwin.world.terrain.*;

public class Tile {
	private final Terrain terrain;
	private final List<Entity> entities;
	
	public Tile(Terrain terrain, Entity... entities) {
		this.terrain = terrain;
		this.entities = new ArrayList<Entity>(Arrays.asList(entities));
	}
	
	public Tile(Terrain terrain, List<Entity> entities) {
		this.terrain = terrain;
		this.entities = entities;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
	
	/**
	 * Sorted from bottom to top (bottom first)
	 * @return
	 */
	public List<Entity> getEntities() {
		return entities;
	}
}
