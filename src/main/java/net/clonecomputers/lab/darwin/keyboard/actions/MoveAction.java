package net.clonecomputers.lab.darwin.keyboard.actions;

import net.clonecomputers.lab.darwin.*;
import net.clonecomputers.lab.darwin.keyboard.*;

public class MoveAction implements KeyAction {
	private final int dx, dy;
	
	public MoveAction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public void doAction(DarwinsRevenge r) {
		r.world.getLevel().moveEntity(r.player, dx, dy, true);
		r.levelRenderer.moveTo(r.player.getX(), r.player.getY());
	}

}
