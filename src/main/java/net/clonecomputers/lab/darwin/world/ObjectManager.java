package net.clonecomputers.lab.darwin.world;

import java.util.*;

import net.clonecomputers.lab.darwin.world.entity.*;
import net.clonecomputers.lab.darwin.world.terrain.*;

import com.google.common.collect.*;

public class ObjectManager<T> {
	private static ObjectManager<Entity> entityManager = null;
	private static ObjectManager<Terrain> terrainManager = null;
	
	private int maxId = 0;
	private BiMap<Integer,Class<? extends T>> types = HashBiMap.create(256);
	
	private ObjectManager() {
		
	}
	
	public static ObjectManager<Entity> getEntityManager() {
		if(entityManager == null) entityManager = new ObjectManager<Entity>();
		return entityManager;
	}
	
	public static ObjectManager<Terrain> getTerrainManager() {
		if(terrainManager == null) terrainManager = new ObjectManager<Terrain>();
		return terrainManager;
	}
	
	/**
	 * Register a new type of terrain.
	 * This should be done exactly for each type of terrain, probably in a {@code static} block.
	 * @param type the Class of the new terrain-type
	 * @throws IllegalArgumentException if type is already registered
	 * @return the id of the new terrain-type
	 */
	public int registerType(Class<? extends T> type) {
		types.put(maxId++, type);
		return maxId - 1;
	}
	
	public int getNumTypes() {
		return maxId;
	}

	public Class<? extends T> getType(int id) {
		return types.get(id);
	}
	
	public int getId(Class<? extends T> type) {
		return types.inverse().get(type);
	}
	
	public Set<Class<? extends T>> getTypes() {
		return Collections.unmodifiableSet(types.values());
	}
}
