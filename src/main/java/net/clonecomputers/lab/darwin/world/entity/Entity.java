package net.clonecomputers.lab.darwin.world.entity;

import net.clonecomputers.lab.darwin.world.*;

public abstract class Entity extends WorldObject {
	protected static void registerType(int id, Class<? extends Entity> type) {
		WorldObject.registerType(id + 128, type);
	}
}
