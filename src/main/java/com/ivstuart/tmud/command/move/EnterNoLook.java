package com.ivstuart.tmud.command.move;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.common.MobState;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.person.movement.MoveManager;
import com.ivstuart.tmud.state.util.RoomManager;
import org.apache.log4j.Logger;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.constants.DoorState;
import com.ivstuart.tmud.state.Door;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.Track;

public class EnterNoLook extends BaseCommand {

	private static final Logger LOGGER = Logger.getLogger(EnterNoLook.class);

	@Override
	public void execute(Mob mob, String input) {

		Room room = mob.getRoom();

		if (mob.getFight().isFighting()) {
			mob.out("You can not move while your fighting! Type flee or disengage to exit from combat");
			return;
		}


		if (mob.getFight().isEngaged()) {
			mob.out("You can not move while being engaged in combat!");
			return;
		}

		if (mob.getMobStatus().isHidden()) {
			mob.out("You can not move while hidding!");
			return;
		}

		if (mob.getMv().getValue() < 0) {
			mob.out("You can not move you are out of movement and too tired!");
			return;
		}

		if (!mob.getState().canMove()) {
			mob.out("You can not move you are "+mob.getState().getDesc());
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

		MoveManager.move(mob,room,destination,exit);

		Track track = new Track();

		// TODO FIXME should this be refactored.
		if (mob.getHp().getPercentageLeft() < 10) {
			track.setBlood(true);
		}

		track.setWho(mob.getName());
		track.setDirection(exit.getId());

		room.addTrack(track);

		// walk fly swim teleport run sneak etc.....
		if (mob.isRunning()) {
			mob.out("You run " + exit.getId());
			mob.getMv().deduct(10);
		}
		else if (mob.isSneaking()) {
			mob.out("You sneak " + exit.getId());
			mob.getMv().deduct(5);
		}
		else if (mob.isFlying()) {
			mob.out("You fly " + exit.getId());
			mob.getMv().deduct(1);
		}
		else {
			mob.out("You walk " + exit.getId());
			mob.getMv().deduct(4);
		}
		// fly 1 point. walk 4. walk on path 2 points.

		// check room for followers and issue move command to any followers.
		for (Mob follower : room.getFollowers(mob)) {
			follower.out("You follow "+mob.getName()+" out of the room");
			CommandProvider.getCommand(EnterNoLook.class).execute(follower, exit.getId());
		}
	}

}