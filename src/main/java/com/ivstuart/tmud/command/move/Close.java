/*
 *  Copyright 2024. Ivan Stuart
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
import com.ivstuart.tmud.constants.DoorState;
import com.ivstuart.tmud.state.items.Chest;
import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.places.Door;
import com.ivstuart.tmud.state.places.Exit;

public class Close extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        Item item = mob.getRoom().getInventory().get(input);

        if (item != null) {
            closeItem(mob, item);
            return;
        }

        Exit exit = mob.getRoom().getExit(input);

        if (exit == null) {
            mob.out("No visible exit in direction " + input);
            return;
        }

        Door door = exit.getDoor();

        if (door == null) {
            mob.out("No visible door in direction " + input);
            return;
        }

        if (door.getState() == DoorState.CLOSED) {
            mob.out("Door is already closed!");
            return;
        }

        if (door.getState() == DoorState.LOCKED) {
            mob.out("Door is locked!");
            return;
        }

        door.setState(DoorState.CLOSED);

        mob.out("You close a door");
    }

    private void closeItem(Mob mob, Item item) {

        if (!(item instanceof Chest)) {
            mob.out("That item can not be closed");
            return;
        }

        Chest chest = (Chest) item;

        if (chest.getState() == DoorState.CLOSED) {
            mob.out("That item is already closed");
            return;
        }

        if (chest.getState() == DoorState.LOCKED) {
            mob.out("That item is locked and is already closed");
            return;
        }

        mob.out("You close a " + chest.getBrief());

        chest.setState(DoorState.CLOSED);

    }
}
