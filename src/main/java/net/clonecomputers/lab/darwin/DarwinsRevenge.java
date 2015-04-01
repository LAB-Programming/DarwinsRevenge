package net.clonecomputers.lab.darwin;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.lang.reflect.*;

import javax.swing.*;

import net.clonecomputers.lab.darwin.rendering.*;
import net.clonecomputers.lab.darwin.rendering.tilesets.*;
import net.clonecomputers.lab.darwin.world.*;
import net.clonecomputers.lab.darwin.world.generate.*;

public class DarwinsRevenge implements Runnable {
	private LevelRenderer renderer;
	private World world;
	
	private final long NANOS_PER_FRAME = (long) 1e9/60; // (10^9 / target fps)
	
	private volatile boolean running = true;
	
	private int lastFps = -1;
	
	private JFrame window;
	
	public DarwinsRevenge() {
		world = new World(new SimpleWorldGenerator());
		Tileset tileset;
		//tileset = new SimpleTileset();
		try {
			tileset = new ImageTileset("/tileset.png", new Dimension(10,12));
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		renderer = new LevelRenderer(world.getLevel(), tileset);
		
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
			}
			
			update(updateLength);
			
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
