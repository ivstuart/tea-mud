package com.ivstuart.tmud.command.move;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.constants.DoorState;
import com.ivstuart.tmud.person.movement.MoveManager;
import com.ivstuart.tmud.state.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnterNoLook extends BaseCommand {

	private static final Logger LOGGER = LogManager.getLogger();

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

		if (mob.getMv() != null && mob.getMv().getValue() < 0) {
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

        if (exit.isGuarded(mob)) {
            mob.out("The exit in direction " + exit.getId() + " is guarded.");
			return;
		}

		Room destination = exit.getDestinationRoom();

		if (destination == null) {
			mob.out("The exit in direction " + exit.getId()+" is not built yet!");
			return;
		}

        if (mob.getMv() != null) {
            // walk fly swim teleport run sneak etc.....

			int movemod = mob.getBurden().getMovemod();

			if (room.isWater() || exit.isSwim() && !mob.isFlying()) {
				if (!mob.getMv().deduct(10 * movemod)) {
					mob.out("You can not swim you are out of movement and too tired!");
					return;
				}
				mob.out("You swim " + exit.getId());
			}
			if (mob.isRunning()) {
				if (!mob.getMv().deduct(10 * movemod)) {
					mob.out("You can not run you are out of movement and too tired!");
					return;
                }
                mob.out("You run " + exit.getId());
            } else if (mob.isSneaking()) {
				if (!mob.getMv().deduct(4 * movemod)) {
					mob.out("You can not sneak you are out of movement and too tired!");
					return;
                }
                mob.out("You sneak " + exit.getId());

            } else if (mob.isFlying()) {
				if (!mob.getMv().deduct(1 * movemod)) {
					mob.out("You can not fly you are out of movement and too tired!");
					return;
                }
                mob.out("You fly " + exit.getId());

            } else {
				if (!mob.getMv().deduct(2 * movemod)) {
					mob.out("You can not walk you are out of movement and too tired!");
					return;
                }
                mob.out("You walk " + exit.getId());

            }
        }

		MoveManager.move(mob,room,destination,exit);

		Track track = new Track();

		// TODO FIXME should this be refactored.
		if (mob.getHp().getPercentageLeft() < 10) {
			track.setBlood(true);
		}

		track.setWho(mob.getName());
		track.setDirection(exit.getId());

		room.addTrack(track);


        // fly 1 point. walk 4. walk on path 2 points.

		// check room for followers and issue move command to any followers.
		for (Mob follower : room.getFollowers(mob)) {
			follower.out("You follow "+mob.getName()+" out of the room");
			CommandProvider.getCommand(EnterNoLook.class).execute(follower, exit.getId());
		}
	}

}