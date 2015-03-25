package net.clonecomputers.lab.darwin.rendering;

import java.awt.*;

import javax.swing.*;

import net.clonecomputers.lab.darwin.world.*;

public class LevelRenderer extends JPanel {
	private Level levelToRender;
	private Tileset tileset;
	private Dimension tileSize;
	private int centerX = 0;
	private int centerY = 0;
	
	public LevelRenderer(Level levelToRender, Tileset tileset) {
		setTileset(tileset);
		setLevel(levelToRender);
	}
	
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
		repaint();
	}
	
	public void repaintTiles(int x, int y, int width, int height) {
		repaint((x - centerX) * tileSize.width + getWidth() / 2, (centerY - y) * tileSize.height + getHeight() / 2, width * tileSize.width, height * tileSize.height);
	}
	
	public void repaintTile(int x, int y) {
		repaintTiles(x, y, 1, 1);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Rectangle areaToDraw = g.getClipBounds();
		int minX = (int)Math.floor((areaToDraw.x - getWidth()/2) / (double)tileSize.width) + centerX;
		int maxX = (int)Math.ceil((areaToDraw.x - getWidth()/2 + areaToDraw.width) / (double)tileSize.width) + centerX;
		int minY = (int)Math.floor((areaToDraw.y - getHeight()/2) / (double)tileSize.height) + centerY;
		int maxY = (int)Math.ceil((areaToDraw.y - getHeight()/2 + areaToDraw.height) / (double)tileSize.height) + centerY;
		System.out.println("rendering from ("+minX+","+minY+") to ("+maxX+","+maxY+")");
		for(int x = minX; x <= maxX; x++) {
			for(int y = minY; y <= maxY; y++) {
				int gx = ((x - centerX) * tileSize.width) + (getWidth() / 2);
				int gy = (getHeight() / 2) - ((y - centerY) * tileSize.height);
				Graphics shiftedG = g.create(gx, gy, tileSize.width, tileSize.height);
				//((Graphics2D)shiftedG).translate(gx, gy); // done by g.create(x, y, width, height)
				tileset.drawTileImage(levelToRender.getTile(x, y), shiftedG);
			}
		}
	}
}
