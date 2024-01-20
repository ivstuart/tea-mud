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
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;

public class Run extends BaseCommand {

    // Run w w w n n

    @Override
    public void execute(Mob mob, String input) {

        mob.setRunning(true);

        for (String direction : input.split(" ")) {

            Room previousRoom = mob.getRoom();
            Room currentRoom = null;

            if (mob.getMv().deduct(10)) {
                CommandProvider.getCommand(EnterNoLook.class).execute(mob,
                        direction);

                currentRoom = mob.getRoom();
            } else {
                mob.out("You have run out of puff to run!");
                break;
            }

            if (previousRoom == currentRoom) {
                break;
            }
        }

        mob.setRunning(false);

    }

}