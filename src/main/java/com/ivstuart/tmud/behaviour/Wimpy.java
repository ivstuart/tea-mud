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
import com.ivstuart.tmud.command.combat.Flee;
import com.ivstuart.tmud.common.DiceRoll;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Wimpy extends BaseBehaviour {

    private static Logger LOGGER = LogManager.getLogger();


    public Wimpy() {
        parameter = 50;
    }

    @Override
    public String getId() {
        return mob.getId();
    }

    @Override
    public boolean tick() {
        // If any players visible in same location then random pick one to attack.
        if (!mob.getFight().isFighting()) {
            LOGGER.debug(mob.getName() + " is not fighting hence will not wimpy");
            return false;
        }

        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName()+" is does not feel like being wimpy");
            return false;
        }

        // Absolute amount or percentage to use here. Coded as an absolute amount for now.
        if (parameter2 < mob.getHp().getValue()) {
            LOGGER.debug(mob.getName()+" wimpy not reached yet");
            return false;
        }

        CommandProvider.getCommand(Flee.class).execute(mob,null);

        return false;


    }

}
