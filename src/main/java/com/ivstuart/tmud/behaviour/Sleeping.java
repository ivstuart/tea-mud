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
import com.ivstuart.tmud.common.MobState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Sleeping extends BaseBehaviour {

    private final static Logger LOGGER = LogManager.getLogger();

    public Sleeping() {
        parameter = 10;
    }

    @Override
    public String getId() {
        return mob.getId();
    }

    @Override
    public boolean tick() {

        // If not engage and if damaged then sleep
        if (mob.getFight().isEngaged()) {
            LOGGER.debug("Sleeper mob is fighting so it will not sleep");
            return false;
        }

        if (mob.getHp().isMaximum()) {
            return false;
        } else {
            mob.setState(MobState.SLEEP);
        }
        // If nighttime 10 % chance of sleeping else 10 % of awaking
        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName() + " is does not feel like being sleepy this tick");
            return false;
        }

        // If state awake and not fighting then change state to sleeping.
        mob.setState(MobState.SLEEP);

        return false;


    }

}
