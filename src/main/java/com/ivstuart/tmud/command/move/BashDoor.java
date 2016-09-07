/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.move;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.constants.DoorState;
import com.ivstuart.tmud.state.Door;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Mob;

public class BashDoor extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        Exit exit = mob.getRoom().getExit(input);

        if (exit == null) {
            mob.out("There is no door to bash in the direction " + input);
            return;
        }

        if (exit.getDoor() == null) {
            mob.out("There is no door to bash in the direction " + exit.getName());
            return;
        }
        Door door = exit.getDoor();
        if (door.getState() == DoorState.OPEN) {
            mob.out("Door is already laying in an open state");
            return;
        }
        if (door.getState() == DoorState.BROKEN) {
            mob.out("Door is already laying in an broken mess");
            return;
        }

        if (!mob.getMv().deduct(10)) {
            mob.out("You are too tired to try to bash down a door");
            return;
        }

        if (!door.isBashable()) {
            mob.out("Ouch!! You are not strong enough to break down this door");
            return;
        }

        if (door.getStrength() > mob.getPlayer().getAttributes().getSTR().getValue()) {
            mob.out("Ouch! You are not strong enough to break down this door");
            return;
        }

        if (DiceRoll.ONE_D100.rollLessThanOrEqualTo(50)) {
            mob.out("Ouch. You are not strong enough to break down this door");
            return;
        }

        mob.out("You successfully bash the door down, it is now broken");
        door.setState(DoorState.BROKEN);
    }
}