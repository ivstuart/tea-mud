/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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