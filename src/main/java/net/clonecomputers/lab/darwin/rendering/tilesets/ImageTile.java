package net.clonecomputers.lab.darwin.rendering.tilesets;

import java.awt.image.*;
import java.util.*;


public class ImageTile {
	
	private LookupOp colorFilter;
	private int tileX;
	private int tileY;
	
	// Demo values for now
	public ImageTile(int x, int y) {
		byte gray = (byte) ((int) (Math.random()*2)*64 + 127); //either 127 or 191
		byte[] r = new byte[256];
		byte[] g = new byte[256];
		byte[] b = new byte[256];
		Arrays.fill(r, (byte) gray);
		Arrays.fill(g, (byte) gray);
		Arrays.fill(b, (byte) gray);
		r[0] = 0; g[0] = 0; b[0] = 0;
		colorFilter = new LookupOp(new ByteLookupTable(0, new byte[][]{r, g, b}), null);
		tileX = x;
		tileY = y;
	}
	
	public BufferedImageOp getColorFilter() {
		return colorFilter;
	}
	
	public int getTileX() {
		return tileX;
	}
	
	public int getTileY() {
		return tileY;
	}
	
}
