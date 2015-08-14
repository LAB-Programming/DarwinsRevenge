package net.clonecomputers.lab.darwin.rendering.windows;

import java.util.Map;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import net.clonecomputers.lab.darwin.rendering.TileImageProperties;

public class TileWindow {

	/**
	 * Hmm... should we back TileWindow with a Table or with a List?
	 * Which would be faster... list I am thinking? I will see how
	 * slow using tables ends up being. They should be fine except
	 * for resizing
	 */
	private Table<Integer, Integer, TileImageProperties> view;
	private Table<Integer, Integer, TileImageProperties> buffer;
	private int width;
	private int height;
	
	private int cursorRow = 0;
	private int cursorCol = 0;
	
	private int curFgColor = 0x000000;
	private int curBgColor = 0x000000;
	
	TileWindow(int width, int height) {
		view = HashBasedTable.create(height, width);
		buffer = HashBasedTable.create(height, width);
		this.width = width;
		this.height = height;
	}
	
	public void setCursor(int row, int col) {
		if (row < 0) {
			cursorRow = 0;
		} else {
			cursorRow = Math.min(height - 1, row);
		}
		if (col < 0) {
			cursorCol = 0;
		} else {
			cursorCol = Math.min(width - 1, col);
		}
	}
	
	public void moveCursor(int dy, int dx) {
		if (dy < 0) {
			cursorRow = Math.max(0, cursorRow + dy);
		} else {
			cursorRow = Math.min(height - 1, cursorRow + dy);
		}
		if (dx < 0) {
			cursorCol = Math.max(0, cursorCol + dx);
		} else {
			cursorCol = Math.min(width - 1, cursorCol + dx);
		}
	}
	
	public int getCursorRow() {
		return cursorRow;
	}
	
	public int getCursorCol() {
		return cursorCol;
	}
	
	public void setFgColor(int color) {
		if (color < 0 || color > 0xFFFFFF) {
			throw new IllegalArgumentException("FgColor (" + color + ") must be between 0 and 0xFFFFFF");
		}
		curFgColor = color;
	}
	
	public void setBgColor(int color) {
		if (color < 0 || color > 0xFFFFFF) {
			throw new IllegalArgumentException("BgColor (" + color + ") must be between 0 and 0xFFFFFF");
		}
		curBgColor = color;
	}
	
	public void setColor(int fgColor, int bgColor) {
		setFgColor(fgColor);
		setBgColor(bgColor);
	}
	
	public int getFgColor() {
		return curFgColor;
	}
	
	public int getBgColor() {
		return curBgColor;
	}
	
	public void printRaw(int tileId) {
		buffer.put(cursorRow, cursorCol, new TileImageProperties(tileId, curFgColor, curBgColor));
		if (cursorCol == width - 1) {
			if (cursorRow != height - 1) {
				cursorCol = 0;
				cursorRow++;
			}
		} else {
			++cursorCol;
		}
	}
	
	public void print(boolean b) {
		print(Boolean.toString(b));
	}
	
	public void print(char c) {
		printRaw((int) c);
	}
	
	public void print(char[] s) {
		for (char c : s) {
			if (cursorRow == height - 1 && cursorCol == width - 1) {
				print(c);
				break;
			} else {
				print(c);
			}
		}
	}
	
	public void print(double d) {
		print(Double.toString(d));
	}
	
	public void print(float f) {
		print(Float.toString(f));
	}
	
	public void print(int i) {
		print(Integer.toString(i));
	}
	
	public void print(long l) {
		print(Long.toString(l));
	}
	
	public void print(Object o) {
		print(o.toString());
	}
	
	public void print(String s) {
		print(s.toCharArray());
	}
	
	public void printf(String format, Object... args) {
		print(String.format(format, args));
	}
	
	public void refresh() {
		view = HashBasedTable.create(buffer);
	}
	
	public void resize(int width, int height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Width and Height must be greater than 0 (w, h): ("
											   + width + ", " + height + ")");
		}
		if (height < this.height) {
			Map<Integer, Map<Integer, TileImageProperties>> vRowMap = view.rowMap();
			Map<Integer, Map<Integer, TileImageProperties>> bRowMap = buffer.rowMap();
			for (int r = height; r < this.height; ++r) {
				vRowMap.remove(r);
				bRowMap.remove(r);
			}
		}
		this.height = height;
		if (width < this.width) {
			Map<Integer, Map<Integer, TileImageProperties>> vColMap = view.columnMap();
			Map<Integer, Map<Integer, TileImageProperties>> bColMap = buffer.columnMap();
			for (int c = width; c < this.width; ++c) {
				vColMap.remove(c);
				bColMap.remove(c);
			}
		}
		this.width = width;
	}
	
	/**
	 * Friendly access because the program as a whole shouldn't really need
	 * this method and its somewhat confusing since it does not read from the
	 * buffer.
	 */
	TileImageProperties getViewTile(int r, int c) {
		return view.get(r, c);
	}
}
