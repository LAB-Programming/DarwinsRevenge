package net.clonecomputers.lab.darwin.keyboard;

import java.awt.event.*;
import java.util.*;
import java.util.concurrent.*;

public class KeyHandler implements KeyListener, Iterable<KeyAction> {
	
	private Queue<KeyAction> actions = new ConcurrentLinkedQueue<KeyAction>();
	
	private KeyMap keyMap;
	
	public KeyHandler(KeyMap keyMap) {
		this.keyMap = keyMap;
	}
	
	public void setKeyMap(KeyMap keyMap) {
		this.keyMap = keyMap;
	}
	
	public KeyMap getKeyMap() {
		return keyMap;
	}
	
	public KeyAction pollNextAction() {
		return actions.poll();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		KeyAction action = keyMap.getAction(e.getKeyCode());
		if(action != null) {
			actions.offer(action);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public Iterator<KeyAction> iterator() {
		return new KeyActionIterator();
	}
	
	private class KeyActionIterator implements Iterator<KeyAction> {

		@Override
		public boolean hasNext() {
			return !actions.isEmpty();
		}

		@Override
		public KeyAction next() {
			return actions.poll();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}

}
