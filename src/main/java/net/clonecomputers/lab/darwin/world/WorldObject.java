package net.clonecomputers.lab.darwin.world;

public abstract class WorldObject {
	protected Tile tile;
	
	protected static void registerType(int id, Class<? extends WorldObject> type) {
		WorldObjectManager.getObjectManager().registerType(id, type);
	}

	public abstract int getImageId();
	
	public abstract int getFgColor();
	public abstract int getBgColor();
	
	/**
	 * Should only be called from the Tile class
	 * @param tile
	 */
	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
	// Demo function, will be removed eventually
	protected int createFgColor() {
		int gray = ((int) (Math.random()*2)*64 + 127); //either 127 or 191
		return gray | (gray<<8) | (gray<<16);
	}
	
	public int getX() {
		return tile.x;
	}
	
	public int getY() {
		return tile.y;
	}
}
