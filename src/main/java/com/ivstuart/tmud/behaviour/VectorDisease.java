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
import com.ivstuart.tmud.person.statistics.diseases.Disease;
import com.ivstuart.tmud.person.statistics.diseases.DiseaseFactory;
import com.ivstuart.tmud.state.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VectorDisease extends BaseBehaviour {

    private static Logger LOGGER = LogManager.getLogger();


    public VectorDisease() {
        parameter = 50; // precentage
    }

    @Override
    public String getId() {
        return mob.getId();
    }

    @Override
    public boolean tick() {

        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName() + " is does not feel like biting");
            return false;
        }

        Mob playerMob = mob.getRoom().getRandomPlayer();

        if (playerMob == null) {
            LOGGER.debug("No players to bite.");
            return false;
        }

        if (mob.getMobAffects().getDiseases().isEmpty() &&
                playerMob.getMobAffects().getDiseases().isEmpty()) {
            LOGGER.debug("No diseases to transmit by bite");
            return false;
        }

        for (Disease disease : mob.getMobAffects().getDiseases()) {
            if (disease.isVector()) {
                Disease.infect(playerMob, disease);
            }
        }

        for (Disease disease : playerMob.getMobAffects().getDiseases()) {
            if (disease.isVector()) {
                Disease.infect(mob, disease);
            }
        }

        return false;
    }

    @Override
    public void setParameter3(String parameter3) {
        this.parameter3 = parameter3;
        Disease disease = DiseaseFactory.createClass(parameter3);
        disease.setMob(mob);
        disease.setDecription(parameter3);
        mob.getMobAffects().add(disease.getId(), disease);
    }
}
