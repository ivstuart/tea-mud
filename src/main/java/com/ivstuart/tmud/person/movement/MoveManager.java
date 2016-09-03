package com.ivstuart.tmud.person.movement;

import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.util.RoomManager;

import java.util.List;

public class MoveManager {

	public static void move(Mob mob_, Room destination) {

		Room room = mob_.getRoom();

		room.remove(mob_);

		destination.add(mob_);

	}

	// TODO fix all calling code to provide the movementType this is a hack
	public static void move(Mob mob_, Room sourceRoom_, Room destinationRoom_, Exit exit_) {
		move(mob_, sourceRoom_, destinationRoom_, exit_, "walks");
	}

	public static void move(Mob mob_, Room sourceRoom_, Room destinationRoom_, Exit exit_, String movementType) {

		sourceRoom_.remove(mob_);

		sourceRoom_.out(new Msg(mob_, "<S-NAME> " + movementType + " " + exit_.getId()));

		destinationRoom_.add(mob_);

		destinationRoom_.out(new Msg(mob_,"<S-NAME> arrives from the "+ RoomManager.reverseDirection(exit_.getId())));

	}

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

	public static void move(Mob mob, String down) {

		Exit exit = mob.getRoom().getExit(down);

		if (exit == null) {
			mob.out("Strong winds buffet you around and you fail to fall straight down");
			return;
		}

		MoveManager.move(mob, mob.getRoom(), exit.getDestinationRoom(), exit, "falling");
	}
}
