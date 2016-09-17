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

import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.command.state.Stand;
import com.ivstuart.tmud.common.DiceRoll;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Stander extends BaseBehaviour {

    private static Logger LOGGER = LogManager.getLogger();


    public Stander() {
        parameter = 50; // precentage
    }

    @Override
    public String getId() {
        return mob.getId();
    }

    @Override
    public boolean tick() {

        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName() + " is does not feel like standing");
            return false;
        }

        if (!mob.getFight().isGroundFighting()) {
            LOGGER.debug(mob.getName() + " no need to stand");
            return false;
        }

        if (mob.getMobStatus().isGroundFighting()) {
            LOGGER.debug(mob.getName() + " recently engaged in ground fighting");
            return false;
        }

        CommandProvider.getCommand(Stand.class).execute(mob, null);
        return false;
    }

}
