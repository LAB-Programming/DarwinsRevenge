package net.clonecomputers.lab.darwin;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class DarwinsRevenge implements Runnable {
	
	private final long NANOS_PER_FRAME = 1000000000/60; // (10^9 / target fps)
	
	private volatile boolean running = true;
	
	private int lastFps = -1;
	
	private JFrame window;
	
	public DarwinsRevenge() {
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
		window.setResizable(false);
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
			if (timeSinceFpsCalc > 1000000000) {
				lastFps = framesSinceSecond;
				timeSinceFpsCalc = 0;
				framesSinceSecond = 0;
			}
			
			update(updateLength);
			
			Graphics2D g = null;
			try {
				g = (Graphics2D) bs.getDrawGraphics();
				// move origin of graphics so that we don't draw behind menubar in windowed mode
				g.translate(window.getRootPane().getX(), window.getRootPane().getY());
				render(g);
			} finally {
				if (g != null) g.dispose();
			}
			if (!bs.contentsLost()) {
				bs.show();
			}
			
			try {
				Thread.sleep(
						Math.max((lastLoopTime-System.nanoTime() + NANOS_PER_FRAME)/1000000, 0L)
					);
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
		g.drawString("FPS: " + lastFps, 5, 20);
	}

	public static void main(String[] args) {
		DarwinsRevenge game = new DarwinsRevenge();
		Thread gameRunner = new Thread(game);
		gameRunner.start();
	}

}
