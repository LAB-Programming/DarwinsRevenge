package net.clonecomputers.lab.darwin.rendering;

import java.awt.*;

public class GuiRenderer {
	
	private Dimension windowSize;
	private Tileset tileset;
	private Dimension tileSize;
	private boolean fullRedraw = true;
	
	public GuiRenderer(Dimension windowSize, Tileset tileset) {
		this.windowSize = windowSize;
		this.tileset = tileset;
		tileSize = tileset.getTileDimensions();
	}
	
	public void setTileset(Tileset t) {
		if (!t.equals(tileset)) {
			tileset = t;
			tileSize = t.getTileDimensions();
			fullRedraw = true;
		}
	}
	
	public void setWindowSize(Dimension newSize) {
		if (!newSize.equals(windowSize)) {
			windowSize = newSize;
			fullRedraw = true;
		}
	}

	public Graphics2D paintAndResize(Graphics2D g) {
		if (fullRedraw) {
			g = blackPadWindow(g);
			for (int i = 0; i < windowSize.width; i += tileSize.width) {
				Graphics topSmallG = g.create(i, 0, tileSize.width, tileSize.height);
				Graphics bottomSmallG = g.create(i, windowSize.height - tileSize.height, tileSize.width, tileSize.height);
				tileset.drawCharacter(0, 0x808080, 0x808080, topSmallG);
				tileset.drawCharacter(0, 0x808080, 0x808080, bottomSmallG);
			}
		}
		fullRedraw = false;
		g.setClip(0, tileSize.height, windowSize.width, windowSize.height - 2*tileSize.height);
		return g;
	}

	private Graphics2D blackPadWindow(Graphics2D g) {
		int xOffset = windowSize.width % tileSize.width;
		int yOffset = windowSize.height % tileSize.height;
		if (xOffset != 0 || yOffset != 0) {
			g.setBackground(Color.BLACK);
			g.clearRect(0, 0, windowSize.width, windowSize.height);
		}
		g.translate(xOffset/2, yOffset/2);
		g.clipRect(0, 0, windowSize.width - xOffset, windowSize.height - yOffset);
		return g;
	}
	
}
