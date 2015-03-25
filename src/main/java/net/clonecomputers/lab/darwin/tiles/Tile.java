package net.clonecomputers.lab.darwin.tiles;

import java.awt.image.BufferedImageOp;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.util.Arrays;


public class Tile {
	
	private LookupOp colorFilter;
	private int tileX;
	private int tileY = 2;
	
	// Demo values for now
	public Tile() {
		byte gray = (byte) ((int) (Math.random()*2)*64 + 127); //either 127 or 191
		byte[] r = new byte[256];
		byte[] g = new byte[256];
		byte[] b = new byte[256];
		Arrays.fill(r, (byte) gray);
		Arrays.fill(g, (byte) gray);
		Arrays.fill(b, (byte) gray);
		r[0] = 0; g[0] = 0; b[0] = 0;
		colorFilter = new LookupOp(new ByteLookupTable(0, new byte[][]{r, g, b}), null);
		tileX = (int) (Math.random()*3)*2 + 10; // either 10, 12, or 14
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
