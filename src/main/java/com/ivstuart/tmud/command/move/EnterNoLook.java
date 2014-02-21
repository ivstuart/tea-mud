package com.ivstuart.tmud.command.move;

import org.apache.log4j.Logger;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.constants.DoorState;
import com.ivstuart.tmud.state.Door;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.Track;

public class EnterNoLook implements Command {

	private static final Logger LOGGER = Logger.getLogger(EnterNoLook.class);

	@Override
	public void execute(Mob mob, String input) {

		Room room = mob.getRoom();

		if (mob.getFight().isEngaged()) {
			mob.out("You can not move while being attacked!");
			return;
		}

		if (mob.getMobStatus().isHidden()) {
			mob.out("You can not move while hidding!");
			return;
		}

		
		Exit exit = room.getExit(input);

		if (exit == null) {
			mob.out("No visible exit in direction " + input + ".");
			return;
		}

		Door door = exit.getDoor();

		if (door != null && door.getState() != DoorState.OPEN) {
			mob.out("The exit in direction " + exit.getId() + " is not open.");
			return;
		}

		if (exit.isGuarded()) {
			mob.out("The exit in direction " + exit.getId() + " is guarded.");
			return;
		}

		Room destination = exit.getDestinationRoom();

		boolean wasRemoved = room.remove(mob);

		// TODO think about this.
		if (!wasRemoved) {
			LOGGER.warn("Mob was not present to be removed");
		}

		Track track = new Track();

		// TODO FIXME should this be refactored.
		if (mob.getHp().getPercentageLeft() < 10) {
			track.setBlood(true);
		}

		track.setWho(mob.getName());
		track.setDirection(exit.getId());

		room.addTrack(track);

		destination.add(mob);

		// TODO limit movement of wondering monsters
		// mob_.getRoomHistory().add(destination.getId());

		// walk fly swim teleport run sneak etc.....
		mob.out("You walk " + exit.getId());
	}

}