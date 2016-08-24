package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.ExitEnum;
import com.ivstuart.tmud.person.movement.MoveManager;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Patrol extends BaseBehaviour {

	private static Logger LOGGER = LogManager.getLogger();

	private String path;
	private int indexOfPath;

	public Patrol() {
		parameter = 50;
		parameter2 = 2;
		path = "nnneeessswww";
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String getId() {
		return mob.getId();
	}

	@Override
	public void tick() {

		if (mob.getFight().isFighting()) {
			LOGGER.debug(mob.getName()+" is fighting and hence will not patrol");
			return;
		}

		if (mob.getFight().isEngaged()) {
			LOGGER.debug(mob.getName()+" is engaged and hence will not patrol");
			return;
		}

		// TODO have this configurable in mob.txt file.
		if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
			LOGGER.debug(mob.getName()+" is does not feel like patrol");
			return;
		}

		Room currentRoom = mob.getRoom();

		String exitString = path.substring(indexOfPath,indexOfPath+1);

		indexOfPath++;
		if (indexOfPath >= path.length()) {
			indexOfPath=0;
		}

		// LOGGER.debug("Patrolling path "+path+" index "+indexOfPath+" exitString "+exitString);

		exitString = ExitEnum.valueOf(exitString).getDesc();

		Exit exit = currentRoom.getExit(exitString);

		if (exit == null) {
			return;
		}

		MoveManager.move(mob,currentRoom,exit.getDestinationRoom(),exit);

	}

}
