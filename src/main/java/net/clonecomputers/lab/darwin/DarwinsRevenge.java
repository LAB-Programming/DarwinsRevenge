package net.clonecomputers.lab.darwin;

import java.awt.*;
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
	
	private final long NANOS_PER_TICK = 1000000000/1; // (10^9 / target tps)
	
	private volatile boolean running = true;
	
	private int lastTps = -1;
	
	private JFrame window;
	
	public DarwinsRevenge() {
		world = new World(new SimpleWorldGenerator());
		try {
			renderer = new LevelRenderer(world.getLevel(), new ImageTileset("/tileset.png", new Dimension(10,12)));
		} catch (IllegalArgumentException e1) {
			throw new RuntimeException(e1);
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		
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
		window.setContentPane(renderer);
		window.getContentPane().setPreferredSize(new Dimension(800, 300));
		window.pack();
	}

	@Override
	public void run() {
		window.setVisible(true);
		renderer.repaint();
		BufferStrategy bs = window.getBufferStrategy();
		long lastLoopTime = System.nanoTime();
		long timeSinceTpsCalc = 0;
		int ticksSinceSecond = 0;
		while (running) {
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			
			timeSinceTpsCalc += updateLength;
			++ticksSinceSecond;
			if (timeSinceTpsCalc > 1000000000) {
				lastTps = ticksSinceSecond;
				timeSinceTpsCalc = 0;
				ticksSinceSecond = 0;
			}
			
			update(updateLength);
			
			//renderer.repaint();
			
			try {
				Thread.sleep(Math.max((lastLoopTime-System.nanoTime() + NANOS_PER_TICK)/1000000, 0L));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void update(long delta) {
		// TODO update stuff here!
	}
	
	private void render(Graphics2D g) {
		g.setBackground(Color.WHITE);
		g.setColor(Color.BLUE);
		g.clearRect(0, 0, window.getContentPane().getWidth(), window.getContentPane().getHeight());
		g.drawString("TPS: " + lastTps, 5, 20);
	}

	public static void main(String[] args) {
		DarwinsRevenge game = new DarwinsRevenge();
		Thread gameRunner = new Thread(game);
		gameRunner.start();
	}

}
