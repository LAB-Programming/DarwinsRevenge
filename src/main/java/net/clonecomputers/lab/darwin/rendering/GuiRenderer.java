package net.clonecomputers.lab.darwin.rendering;

import java.awt.*;

public class GuiRenderer {
	
	private Dimension windowSize;
	private Rectangle drawArea;
	private Tileset tileset;
	private Dimension tileSize;
	private boolean fullRedraw = true;
	
	public GuiRenderer(Dimension windowSize, Tileset tileset) {
		this.windowSize = windowSize;
		this.drawArea = new Rectangle(windowSize);
		this.tileset = tileset;
		tileSize = tileset.getTileDimensions();
	}
	
	public void setTileset(Tileset t) {
		if (!t.equals(tileset)) {
			tileset = t;
			tileSize = t.getTileDimensions();
			fixDrawArea();
			fullRedraw = true;
		}
	}
	
	public void setWindowSize(Dimension newSize) {
		if (!newSize.equals(windowSize)) {
			windowSize = newSize;
			fullRedraw = true;
		}
	}
	
	private void fixDrawArea() {
		int xOffset = windowSize.width % tileSize.width;
		int yOffset = windowSize.height % tileSize.height;
		drawArea.setBounds(xOffset/2, yOffset/2, windowSize.width - xOffset, windowSize.height - yOffset);
	}

	public Graphics2D paintAndResize(Graphics2D g) {
		if (fullRedraw) {
			fixDrawArea();
			g = blackPadWindow(g);
			g.setClip(drawArea);
			for (int i = 0; i < drawArea.width; i += tileSize.width) {
				Graphics topSmallG = g.create(i, 0, tileSize.width, tileSize.height);
				Graphics bottomSmallG = g.create(i, windowSize.height - tileSize.height, tileSize.width, tileSize.height);
				tileset.drawCharacter(0, 0x808080, 0x808080, topSmallG);
				tileset.drawCharacter(0, 0x808080, 0x808080, bottomSmallG);
			}
			for (int i = tileSize.height; i < drawArea.height - tileSize.height; i += tileSize.height) {
				Graphics topSmallG = g.create(0, i, tileSize.width, tileSize.height);
				Graphics bottomSmallG = g.create(drawArea.width - tileSize.width, i, tileSize.width, tileSize.height);
				tileset.drawCharacter(0, 0x808080, 0x808080, topSmallG);
				tileset.drawCharacter(0, 0x808080, 0x808080, bottomSmallG);
			}
			fullRedraw = false;
		}
		g.clipRect(tileSize.width, tileSize.height, windowSize.width - 2*tileSize.width, windowSize.height - 2*tileSize.height);
		return g;
	}

	private Graphics2D blackPadWindow(Graphics2D g) {
		if (drawArea.width != windowSize.width || drawArea.height != windowSize.height) {
			g.setBackground(Color.BLACK);
			g.clearRect(0, 0, windowSize.width, windowSize.height);
		}
		return g;
	}
	
}
