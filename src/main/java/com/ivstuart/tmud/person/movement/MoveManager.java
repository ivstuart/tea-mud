package com.ivstuart.tmud.person.movement;

import java.util.List;

import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;

public class MoveManager {

	public static void move(Mob mob_, Room destination) {
		mob_.getRoom().remove(mob_);

		destination.add(mob_);

	}

	public static void move(Mob mob_, Room sourceRoom_, Room destinationRoom_) {

		sourceRoom_.remove(mob_);

		destinationRoom_.add(mob_);
	}

	// TODO decide if this method should really be here!
	public static Exit random(Mob mob) {

		// Guard against null pointer.. TODO right or wrong?
		if (mob.getRoom() == null) {
			return null;
		}

		List<Exit> exits = mob.getRoom().getExits();

		/* Guard condition for no exists to flee to */
		if (exits.size() == 0) {
			return null;
		}

		int index = (int) (Math.random() * exits.size());

		Exit myExit = (exits.get(index));

		// mob.out("Attempting to flee in " + myExit + " direction.");

		Room currentRoom = mob.getRoom();

		MoveManager.move(mob, mob.getRoom(), myExit.getDestinationRoom());

		if (currentRoom != mob.getRoom()) {
			mob.getFight().stopFighting();
			mob.getFight().clear();
		}

		return myExit;

	}

}
