package net.clonecomputers.lab.darwin.keyboard;

import java.util.*;

public class SimpleKeyMap implements KeyMap {
	private Map<Integer, KeyAction> map = new HashMap<Integer, KeyAction>();

	@Override
	public KeyAction getAction(int keyCode) {
		return map.get(keyCode);
	}
	
	public KeyAction bindAction(int keyCode, KeyAction action) {
		return map.put(keyCode, action);
	}
	
	public KeyAction unbindAction(int keyCode) {
		return map.remove(keyCode);
	}

}
