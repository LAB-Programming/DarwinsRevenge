package net.clonecomputers.lab.darwin.rendering;

import java.awt.*;

import net.clonecomputers.lab.darwin.world.*;

public class LevelRenderer {
	private Level levelToRender;
	private Tileset tileset;
	private Dimension tileSize;
	private Dimension windowSize;
	private int centerX = 0;
	private int centerY = 0;
	
	public LevelRenderer(Level levelToRender, Tileset tileset) {
		setTileset(tileset);
		setLevel(levelToRender);
	}
	
	public void moveTo(int x, int y) {
		this.centerX = x;
		this.centerY = y;
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
	}
	
	public void setTileset(Tileset t) {
		tileset = t;
		tileSize = t.getTileDimensions();
	}
	
	public void setSize(Dimension windowSize) {
		this.windowSize = windowSize;
	}
	
	public void paintTiles(int x, int y, int width, int height, Graphics g) {
		if(windowSize == null) {
			windowSize = g.getClipBounds().getSize();
		}
		paintRect((x - centerX) * tileSize.width + windowSize.width/2, (centerY - y) * tileSize.height + windowSize.height/2, width * tileSize.width, height * tileSize.height, g);
	}
	
	public void paintTile(int x, int y, Graphics g) {
		paintTiles(x, y, 1, 1, g);
	}
	
	public void paintRect(int x, int y, int width, int height, Graphics g) {
		paint(g.create(x, y, width, height));
	}
	
	private Point cellToGraphics(int x, int y) {
		return cellToGraphics(new Point(x, y));
	}
	
	private Point cellToGraphics(Point cell) {
		return new Point((windowSize.width  / 2) - (tileSize.width  / 2) + ((cell.x - centerX) * tileSize.width ), 
						 (windowSize.height / 2) - (tileSize.height / 2) + ((centerY - cell.y) * tileSize.height));
	}
	
	public void paint(Graphics g) {
		Rectangle areaToDraw = g.getClipBounds();
		if(areaToDraw == null) {
			throw new IllegalArgumentException(
					"The Graphics does not have a clip set, so I don't know how much to draw. " +
					"You might try paintRect or paintTiles, which both set the clip automagically?");
		}
		if(windowSize == null) {
			windowSize = areaToDraw.getSize();
		}
		int minX = (int)Math.floor((areaToDraw.x - windowSize.width/2) / (double)tileSize.width) + centerX;
		int maxX = (int)Math.ceil((areaToDraw.x - windowSize.width/2 + areaToDraw.width) / (double)tileSize.width) + centerX;
		int minY = (int)Math.floor((areaToDraw.y - windowSize.height/2) / (double)tileSize.height) + centerY;
		int maxY = (int)Math.ceil((areaToDraw.y - windowSize.height/2 + areaToDraw.height) / (double)tileSize.height) + centerY;
		for(int x = minX; x <= maxX; x++) {
			for(int y = minY; y <= maxY; y++) {
				Point graphics = cellToGraphics(x,y);
				Graphics shiftedG = g.create(graphics.x, graphics.y, tileSize.width, tileSize.height);
				//((Graphics2D)shiftedG).translate(gx, gy); // done by g.create(x, y, width, height)
				tileset.drawTileImage(levelToRender.getTile(x, y), shiftedG);
			}
		}
	}
}
