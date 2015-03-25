package net.clonecomputers.lab.darwin.world;

import java.util.*;

import com.google.common.collect.*;

public class WorldObjectManager {
	private static WorldObjectManager objectManager;
	private BiMap<Integer,Class<? extends WorldObject>> types = HashBiMap.create(256);
	
	private WorldObjectManager() {
		
	}
	
	public static WorldObjectManager getObjectManager() {
		if(objectManager == null) objectManager = new WorldObjectManager();
		return objectManager;
	}
	
	/**
	 * Register a new type of terrain.
	 * This should be done exactly for each type of terrain, probably in a {@code static} block.
	 * @param id 
	 * @param type the Class of the new terrain-type
	 * @param textureY 
	 * @param textureX 
	 * @throws IllegalArgumentException if type is already registered
	 * @return the id of the new terrain-type
	 */
	public void registerType(int id, Class<? extends WorldObject> type, int textureX, int textureY) {
		//TODO: care about the texture position
		types.put(id, type);
	}
	
	public int getNumTypes() {
		return types.size();
	}

	public Class<? extends WorldObject> getType(int id) {
		return types.get(id);
	}
	
	public int getId(Class<? extends WorldObject> type) {
		return types.inverse().get(type);
	}
	
	public Set<Class<? extends WorldObject>> getTypes() {
		return Collections.unmodifiableSet(types.values());
	}
}
