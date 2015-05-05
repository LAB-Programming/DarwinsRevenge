package net.clonecomputers.lab.darwin;

import net.clonecomputers.lab.darwin.keyboard.KeyAction;
import net.clonecomputers.lab.darwin.keyboard.KeyHandler;
import net.clonecomputers.lab.darwin.rendering.*;
import net.clonecomputers.lab.darwin.rendering.tilesets.*;
import net.clonecomputers.lab.darwin.world.*;
import net.clonecomputers.lab.darwin.world.entity.types.Player;
import net.clonecomputers.lab.darwin.world.generate.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.lang.reflect.*;

import javax.swing.*;

public class DarwinsRevenge implements Runnable {
	private LevelRenderer renderer;
	private World world;
	private KeyHandler keyHandler;
	private Player player;
	
	private final long NANOS_PER_FRAME = (long) 1e9/60; // (10^9 / target fps)
	
	private volatile boolean running = true;
	
	private int lastFps = -1;
	
	private JFrame window;
	
	public DarwinsRevenge() {
		world = new World(new SimpleWorldGenerator());
		Tileset tileset;
		//tileset = new SimpleTileset();
		try {
			tileset = new ImageTileset(new Dimension(10,12), "/tileset.png", "/tileset2.png");
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		renderer = new LevelRenderer(world.getLevel(), tileset);
		keyHandler = new KeyHandler();
		player = new Player();
		Tile startTile = world.getLevel().getTile(0, 0);
		player.setTile(startTile);
		startTile.addEntity(player);
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				
				@Override
				public void run() {
					initGui();
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	private void initGui() {
		window = new JFrame("Darwin's Revenge");
		window.setIgnoreRepaint(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setPreferredSize(new Dimension(800, 300));
		window.pack();
		window.createBufferStrategy(2);
		renderer.setSize(window.getContentPane().getSize());
		window.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				renderer.setSize(window.getContentPane().getSize());
			}
		});
		window.addKeyListener(keyHandler);
	}

	@Override
	public void run() {
		window.setVisible(true);
		BufferStrategy bs = window.getBufferStrategy();
		long lastLoopTime = System.nanoTime();
		long timeSinceFpsCalc = 0;
		int framesSinceSecond = 0;
		while (running) {
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			
			timeSinceFpsCalc += updateLength;
			++framesSinceSecond;
			if (timeSinceFpsCalc > 1e9) {
				lastFps = framesSinceSecond;
				timeSinceFpsCalc = 0;
				framesSinceSecond = 0;
				System.out.println(lastFps);
			}
			
			update(updateLength);
			doKeyActions();
			Graphics2D g = null;
			try {
				g = (Graphics2D) bs.getDrawGraphics();
				// keep it from drawing behind the menu bar
				g.translate(window.getRootPane().getX(), window.getRootPane().getY());
				// tell it what to draw
				g.setClip(window.getContentPane().getBounds());
				render(g);
			} finally {
				if (g != null) g.dispose();
			}
			if (!bs.contentsLost()) {
				bs.show();
			}
			
			try {
				Thread.sleep(Math.max((lastLoopTime-System.nanoTime() + NANOS_PER_FRAME)/1000000, 0L));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void update(long delta) {
		// TODO update stuff here!
		world.update(delta);
	}
	
	private void doKeyActions() {
		for (KeyAction a : keyHandler) {
			switch (a) {
				case PLAYER_MOVE_WEST:
					if (world.getLevel().getTile(player.getX() - 1, player.getY()).isPassable()) {
						world.getLevel().moveEntity(player, player.getX(), player.getY(), -1, 0);
						renderer.moveLeft();
					}
					break;
				case PLAYER_MOVE_EAST:
					if (world.getLevel().getTile(player.getX() + 1, player.getY()).isPassable()) {
						world.getLevel().moveEntity(player, player.getX(), player.getY(), 1, 0);
						renderer.moveRight();
					}
					break;
				case PLAYER_MOVE_NORTH:
					if (world.getLevel().getTile(player.getX(), player.getY() + 1).isPassable()) {
						world.getLevel().moveEntity(player, player.getX(), player.getY(), 0, 1);
						renderer.moveUp();
					}
					break;
				case PLAYER_MOVE_SOUTH:
					if (world.getLevel().getTile(player.getX(), player.getY() - 1).isPassable()) {
						world.getLevel().moveEntity(player, player.getX(), player.getY(), 0, -1);
						renderer.moveDown();
					}
					break;
				default:
					System.err.println("Unrecognized action: " + a);
			}
		}
	}
	
	private void render(Graphics2D g) {
		renderer.paint(g);
		/*
		g.setBackground(Color.WHITE);
		g.setColor(Color.BLUE);
		g.clearRect(0, 0, window.getContentPane().getWidth(), window.getContentPane().getHeight());
		g.drawString("FPS: " + lastFps, 5, 20);
		*/
	}

	public static void main(String[] args) {
		DarwinsRevenge game = new DarwinsRevenge();
		Thread gameRunner = new Thread(game);
		gameRunner.start();
	}

}
