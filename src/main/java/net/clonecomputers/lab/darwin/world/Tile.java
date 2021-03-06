package net.clonecomputers.lab.darwin.world;

import java.util.*;

import net.clonecomputers.lab.darwin.world.entity.*;
import net.clonecomputers.lab.darwin.world.terrain.*;

public class Tile {
	private final Terrain terrain;
	private final List<Entity> entities;
	public final Level level;
	public final int x, y;
	
	public Tile(Level level, int x, int y, Terrain terrain, Entity... entities) {
		this(level, x, y, terrain, Arrays.asList(entities));
	}
	
	public Tile(Level level, int x, int y, Terrain terrain, List<Entity> entities) {
		this.terrain = terrain;
		this.entities = new ArrayList<Entity>(entities);
		this.level = level;
		this.x = x;
		this.y = y;
		terrain.setTile(this);
		for(Entity e: entities) {
			e.setTile(this);
		}
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
	
	/**
	 * Sorted from bottom to top (bottom first)
	 * @return
	 */
	public List<Entity> getEntities() {
		return Collections.unmodifiableList(entities);
	}
	
	public WorldObject getTopmostObject() {
		if (entities.isEmpty()) {
			return terrain;
		}
		return entities.get(entities.size() - 1);
	}
	
	public String toString() {
		return "{"+terrain.getClass().getSimpleName()+": "+entities+"}";
	}
	
	public boolean removeEntity(Entity e) {
		return entities.remove(e);
	}
	
	public boolean addEntity(Entity e) {
		if (isPassable()) return entities.add(e);
		return false;
	}
	
	public boolean isPassable() {
		return terrain.isPassable();
	}
}
