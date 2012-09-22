package com.ivstuart.tmud.behaviour;

import java.util.List;

import org.apache.log4j.Logger;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.Tickable;
import com.ivstuart.tmud.person.movement.MoveManager;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;

public class Wander implements Tickable {

	private static Logger LOGGER = Logger.getLogger(Wander.class);

	private Mob mob;
	private List<Room> rooms;
	private int maxDistance;

	public Wander(Mob mob) {
		this.mob = mob;
	}

	@Override
	public String getId() {
		return mob.getId();
	}

	@Override
	public void tick() {

		if (mob.getFight().isFighting()) {
			return;
		}

		if (DiceRoll.ONE_D100.rollMoreThan(5)) {
			return;
		}

		Exit exit = MoveManager.random(mob);

		if (exit == null) {
			return;
		}

		LOGGER.info("Mob wanders to a new location");

		Room room = mob.getRoom();

		int index = rooms.indexOf(room);

		if (index != -1) {
			rooms = rooms.subList(0, index);
		} else {
			rooms.add(room);
		}

		if (rooms.size() > maxDistance) {
			// Do not wander any more to new locations but can wander to
			// previous locations

			LOGGER.debug("Not wandering at max distance from start location");

		}

	}

}
