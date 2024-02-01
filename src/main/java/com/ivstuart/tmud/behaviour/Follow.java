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

import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.places.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Follow extends BaseBehaviour {

    private final static Logger LOGGER = LogManager.getLogger();


    public Follow() {
        parameter = 50; //aggro percentage
    }

    @Override
    public String getId() {
        return mob.getId();
    }

    @Override
    public boolean tick() {
        // If any players visible in same location then random pick one to attack.
        if (mob.getFight().isFighting()) {
            LOGGER.debug(mob.getName() + " is not fighting hence will not follow");
            return false;
        }

        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName() + " is does not feel like following");
            return false;
        }

        Room room = mob.getRoom();

        if (room == null) {
            LOGGER.warn(mob.getName() + " has no room");
            return false;
        }

        Mob target = room.getRandomPlayer();

        if (target == null) {
            LOGGER.debug(mob.getName() + " has no players to follow");
            return false;
        }

        CommandProvider.getCommandByString("follow").execute(mob, target.getName());
        return false;

    }

}
