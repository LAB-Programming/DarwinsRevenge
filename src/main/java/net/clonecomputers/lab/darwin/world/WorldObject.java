package net.clonecomputers.lab.darwin.world;

public abstract class WorldObject {
	protected Tile tile;
	
	protected static void registerType(int id, Class<? extends WorldObject> type) {
		WorldObjectManager.getObjectManager().registerType(id, type);
	}

	public abstract int getImageId();
	
	/**
	 * Should only be called from the Tile class
	 * @param tile
	 */
	public void setTile(Tile tile) {
		this.tile = tile;
	}
}
