package net.clonecomputers.lab.darwin.world;

public abstract class WorldObject {
	protected static void registerType(int id, Class<? extends WorldObject> type) {
		WorldObjectManager.getObjectManager().registerType(id, type, id % 16, id/16);
	}
}
