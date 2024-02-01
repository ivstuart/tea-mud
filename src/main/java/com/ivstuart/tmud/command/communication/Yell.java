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

package com.ivstuart.tmud.command.communication;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.places.Exit;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.places.Room;

import java.util.LinkedList;
import java.util.List;

/**
 * @author stuarti
 */
public class Yell extends BaseCommand {

    private static final int range = 5;
    private List<Room> area;

    @Override
    public void execute(Mob mob, String input) {

        Yell yell = new Yell();

        yell.area = new LinkedList<>();

        yell.area.add(mob.getRoom()); // Include room presently in

        yell.findArea(mob.getRoom().getExits(), 0);

        yell.yellArea(mob.getId() + " yells, \"" + input + "\"");

        yell.area.clear(); // Prevent memory leak
        yell.area = null;

    }

    private void findArea(List<Exit> exits, int depth) {

        if (depth > range) {
            return;
        }

        for (Exit exit : exits) {
            Room room = exit.getDestinationRoom();
            if (!area.contains(room)) {
                area.add(room);
                this.findArea(room.getExits(), ++depth);
            }
        }
    }

    private void yellArea(String message) {
        for (Room aRoom : area) {
            aRoom.out(message);
        }

    }

}
