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

package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.person.movement.MoveManager;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Tracker extends BaseBehaviour {

    private final static Logger LOGGER = LogManager.getLogger();

    private String target;

    public Tracker() {
        parameter = 50;
        parameter2 = 2;
    }

    @Override
    public String getId() {
        return mob.getId();
    }

    @Override
    public boolean tick() {

        if (mob.getFight().isFighting()) {
            target = mob.getFight().getTarget().getName();
            LOGGER.debug(mob.getName() + " is fighting and hence will not track target");
            return false;
        }

        if (target == null) {
            LOGGER.debug(mob.getName() + " is not currently tracking anyone");
            return false;
        }

        if (mob.getFight().isEngaged()) {
            LOGGER.debug(mob.getName() + " is engaged and hence will not track");
            return false;
        }

        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName() + " is does not feel like tracking this tick");
            return false;
        }

        Room currentRoom = mob.getRoom();

        for (Track track : currentRoom.getTracks()) {
            if (track.getWho().equals(target)) {
                String direction = track.getDirection();

                Exit exit = currentRoom.getExit(direction);

                MoveManager.move(mob, currentRoom, exit.getDestinationRoom(), exit, "walks");
                return false;
            }
        }

        LOGGER.debug(mob.getName() + " has no matching tracks to follow");

        return false;
    }

}
