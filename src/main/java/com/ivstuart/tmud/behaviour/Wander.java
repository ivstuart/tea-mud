package com.ivstuart.tmud.behaviour;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.Tickable;
import com.ivstuart.tmud.person.movement.MoveManager;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;

public class Wander extends BaseBehaviour {

	private static Logger LOGGER = Logger.getLogger(Wander.class);

	private List<Room> rooms;
	private int maxDistance;

	public Wander(Mob mob) {
		this.mob = mob;
		maxDistance = 2;
	}

	public Wander() {
		maxDistance = 2;
	}

	@Override
	public String getId() {
		return mob.getId();
	}

	public int getMaxDistance() {
		return maxDistance;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setMaxDistance(int maxDistance) {
		this.maxDistance = maxDistance;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	@Override
	public void tick() {

		if (mob.getFight().isFighting()) {
			LOGGER.debug(mob.getName()+" is fighting and hence will not wander away");
			return;
		}

		// TODO have this configurable in mob.txt file.
		if (DiceRoll.ONE_D100.rollMoreThan(50)) {
			LOGGER.debug(mob.getName()+" is does not feel like wandering this tick");
			return;
		}

		Room currentRoom = mob.getRoom();

		Exit exit = MoveManager.random(mob);

		if (exit == null) {
			return;
		}

		LOGGER.info("Mob wanders to a new location");

		Room room = mob.getRoom();

		// Lazy init
		if (rooms == null) {
			rooms = new ArrayList<Room>(maxDistance);
		}

		int index = rooms.indexOf(room);

		if (index != -1) {
			// A-B-C-D rooms if in D and goes to C then only need to keep list
			// A-B-C
			rooms = rooms.subList(0, index);
		} else {

			if (rooms.size() > maxDistance) {
				// Do not wander any more to new locations but can wander to
				// previous locations
				
				// Return mob to last known room (think lost sheep on a leash).
				MoveManager.move(mob,currentRoom);
				
				LOGGER.debug("Not wandering at max distance from start location");

			}
			rooms.add(room);
		}

	}

}
