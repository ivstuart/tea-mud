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

		move(mob_,room,destination);

	}

	public static void move(Mob mob_, Room sourceRoom_, Room destinationRoom_) {

		sourceRoom_.remove(mob_);

		//room.out(new Msg(mob,"<S-NAME> walks "+exit.getId()));

		destinationRoom_.add(mob_);

		//destination.out(new Msg(mob,"<S-NAME> arrives from the "+ RoomManager.reverseDirection(exit.getId())));

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

		MoveManager.move(mob, mob.getRoom(), myExit.getDestinationRoom());

		if (currentRoom != mob.getRoom()) {
			mob.getFight().stopFighting();
			mob.getFight().clear();
		}

		return myExit;

	}

}
