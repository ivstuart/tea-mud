package com.ivstuart.tmud.person.movement;

import java.util.List;

import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.util.RoomManager;

public class MoveManager {

	public static void move(Mob mob_, Room destination) {

		Room room = mob_.getRoom();

		room.remove(mob_);

		destination.add(mob_);

	}

	public static void move(Mob mob_, Room sourceRoom_, Room destinationRoom_, Exit exit_) {

		sourceRoom_.remove(mob_);

		sourceRoom_.out(new Msg(mob_,"<S-NAME> walks "+exit_.getId()));

		destinationRoom_.add(mob_);

		destinationRoom_.out(new Msg(mob_,"<S-NAME> arrives from the "+ RoomManager.reverseDirection(exit_.getId())));

	}

	// TODO decide if this method should really be here!
	public static Exit random(Mob mob) {

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

		MoveManager.move(mob, mob.getRoom(), myExit.getDestinationRoom(),myExit);

		if (currentRoom != mob.getRoom()) {
			mob.getFight().stopFighting();
			mob.getFight().clear();
		}

		return myExit;

	}

}
