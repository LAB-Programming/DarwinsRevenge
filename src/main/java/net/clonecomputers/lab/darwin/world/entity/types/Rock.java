package net.clonecomputers.lab.darwin.world.entity.types;

import net.clonecomputers.lab.darwin.world.*;
import net.clonecomputers.lab.darwin.world.entity.*;

public class Rock implements Entity {
	static {
		ObjectManager.getEntityManager().registerType(Rock.class);
	}
}
