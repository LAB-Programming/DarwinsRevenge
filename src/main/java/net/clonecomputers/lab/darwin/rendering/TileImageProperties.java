package net.clonecomputers.lab.darwin.rendering;

public class TileImageProperties {
	public final int tileId;
	public final int fgColor;
	public final int bgColor;
	
	public TileImageProperties(int tileId, int fgColor, int bgColor) {
		this.tileId = tileId;
		this.fgColor = fgColor;
		this.bgColor = bgColor;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof TileImageProperties)) return false;
		TileImageProperties tid = (TileImageProperties)o;
		return tid.tileId == tileId && tid.fgColor == fgColor && tid.bgColor == bgColor;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bgColor;
		result = prime * result + fgColor;
		result = prime * result + tileId;
		return result;
	}
}