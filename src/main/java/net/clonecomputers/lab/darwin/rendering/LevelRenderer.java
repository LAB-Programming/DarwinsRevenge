package net.clonecomputers.lab.darwin.rendering;

import java.awt.*;

import javax.swing.*;

import net.clonecomputers.lab.darwin.world.*;

public class LevelRenderer extends JFrame {
	private Level levelToRender;
	private Tileset tileset;
	private Dimension tileSize;
	private int centerX;
	private int centerY;
	
	public void moveTo(int x, int y) {
		this.centerX = x;
		this.centerY = y;
		repaint();
	}
	
	public void moveUp() {
		moveTo(centerX, centerY + 1);
	}
	
	public void moveDown() {
		moveTo(centerX, centerY - 1);
	}
	
	public void moveLeft() {
		moveTo(centerX - 1, centerY);
	}
	
	public void moveRight() {
		moveTo(centerX + 1, centerY);
	}
	
	public void move(int dx, int dy) {
		moveTo(centerX + dx, centerY + dy);
	}
	
	public void setLevel(Level l) {
		levelToRender = l;
		repaint();
	}
	
	public void setTileset(Tileset t) {
		tileset = t;
		tileSize = t.getTileDimensions();
	}
	
	public void repaintTiles(int x, int y, int width, int height) {
		repaint((x - centerX) * tileSize.width + getWidth() / 2, (centerY - y) * tileSize.height + getHeight() / 2, width * tileSize.width, height * tileSize.height);
	}
	
	public void repaintTile(int x, int y) {
		repaintTiles(x, y, 1, 1);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Rectangle areaToDraw = g.getClipBounds();
		int minX = (int)Math.floor((getWidth()/2 - areaToDraw.x) / (double)tileSize.width);
		int maxX = (int)Math.ceil((getWidth()/2 - areaToDraw.x + areaToDraw.width) / (double)tileSize.width);
		int minY = (int)Math.floor((getHeight()/2 - areaToDraw.y) / (double)tileSize.height);
		int maxY = (int)Math.ceil((getHeight()/2 - areaToDraw.y + areaToDraw.height) / (double)tileSize.height);
		for(int x = minX; x <= maxX; x++) {
			for(int y = minY; y <= maxY; y++) {
				int gx = (x - centerX) * tileSize.width + getWidth() / 2;
				int gy = (centerY - y) * tileSize.height + getHeight() / 2;
				Graphics shiftedG = g.create(gx, gy, tileSize.width, tileSize.height);
				((Graphics2D)shiftedG).translate(gx, gy);
				tileset.drawTileImage(levelToRender.getTile(x, y), shiftedG);
			}
		}
	}
}
