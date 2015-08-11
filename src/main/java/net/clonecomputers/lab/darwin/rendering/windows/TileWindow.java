package net.clonecomputers.lab.darwin.rendering.windows;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import net.clonecomputers.lab.darwin.rendering.TileImageProperties;

public class TileWindow {

	private Table<Integer, Integer, TileImageProperties> view;
	private Table<Integer, Integer, TileImageProperties> buffer;
	private int width;
	private int height;
	
	TileWindow(int width, int height) {
		view = HashBasedTable.create(height, width);
		buffer = HashBasedTable.create(height, width);
		this.width = width;
		this.height = height;
	}
	
	public void refresh() {
		view.putAll(buffer);
	}
	
}
