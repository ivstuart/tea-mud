package com.ivstuart.tmud.state.util;

import com.ivstuart.tmud.state.*;
import org.apache.log4j.Logger;

public class EntityProvider {

	private static final Logger LOGGER = Logger.getLogger(EntityProvider.class);

	public static Item createItem(String itemId) {
		return (Item) World.getItem(itemId).clone();
	}

	public static Mob createMob(String mobId_, String _id) {

		LOGGER.info("Creating mob with id [ " + mobId_ + "]");

		Mob existingMob = World.getMobs().get(mobId_);

		Mob newMob = null;

		// TODO rethink this design as it does not scale for many mob types.
		if (existingMob.isGuard()) {
			newMob = new GuardMob(existingMob);
		} else {
			newMob = new Mob(existingMob);
		}

		// this newMob is a new instance of Mob from world with its own repop
		// room
		newMob.setRepopRoomId(_id);

		return newMob;
	}

	public static Prop createProp(String id_) {

		return World.getProp(id_);
	}
}
