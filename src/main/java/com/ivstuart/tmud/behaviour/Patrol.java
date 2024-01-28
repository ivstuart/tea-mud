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

package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.ExitEnum;
import com.ivstuart.tmud.person.movement.MoveManager;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Patrol extends BaseBehaviour {

    private final static Logger LOGGER = LogManager.getLogger();

    private int indexOfPath;

    public Patrol() {
        parameter = 50;
        parameter2 = 2;
        parameter3 = "nnneeessswww";
    }

    @Override
    public String getId() {
        return mob.getId();
    }

    @Override
    public boolean tick() {

        if (mob.getFight().isFighting()) {
            LOGGER.debug(mob.getName() + " is fighting and hence will not patrol");
            return false;
        }

        if (mob.getFight().isEngaged()) {
            LOGGER.debug(mob.getName() + " is engaged and hence will not patrol");
            return false;
        }

        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName() + " is does not feel like patrol");
            return false;
        }

        Room currentRoom = mob.getRoom();

        String exitString = parameter3.substring(indexOfPath, indexOfPath + 1);

        indexOfPath++;
        if (indexOfPath >= parameter3.length()) {
            indexOfPath = 0;
        }

        // LOGGER.debug("Patrolling path "+path+" index "+indexOfPath+" exitString "+exitString);

        exitString = ExitEnum.valueOf(exitString).getDesc();

        Exit exit = currentRoom.getExit(exitString);

        if (exit == null) {
            return false;
        }

        MoveManager.move(mob, currentRoom, exit.getDestinationRoom(), exit, "walks");

        return false;

    }

}
