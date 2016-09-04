package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.person.movement.MoveManager;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Tracker extends BaseBehaviour {

	private static Logger LOGGER = LogManager.getLogger();

	private List<Room> rooms;

	private String target;

	public Tracker() {
		parameter = 50;
		parameter2 = 2;
	}

	@Override
	public String getId() {
		return mob.getId();
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	@Override
	public void tick() {

		if (mob.getFight().isFighting()) {
			target = mob.getFight().getTarget().getName();
			LOGGER.debug(mob.getName()+" is fighting and hence will not track target");
			return;
		}

		if (target == null) {
			LOGGER.debug(mob.getName()+" is not currently tracking anyone");
			return;
		}

		if (mob.getFight().isEngaged()) {
			LOGGER.debug(mob.getName()+" is engaged and hence will not track");
			return;
		}

		if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
			LOGGER.debug(mob.getName()+" is does not feel like tracking this tick");
			return;
		}

		Room currentRoom = mob.getRoom();

		for (Track track : currentRoom.getTracks()) {
			if (track.getWho().equals(target)) {
				String direction = track.getDirection();

				Exit exit = currentRoom.getExit(direction);

				MoveManager.move(mob, currentRoom, exit.getDestinationRoom(), exit, "walks");
				return;
			}
		}

		LOGGER.debug(mob.getName()+" has no matching tracks to follow");

	}

}
