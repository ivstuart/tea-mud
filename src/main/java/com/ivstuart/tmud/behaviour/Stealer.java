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
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.mobs.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Stealer extends BaseBehaviour {

    private final static Logger LOGGER = LogManager.getLogger();


    public Stealer() {
        parameter = 50;
    }

    @Override
    public String getId() {
        return mob.getId();
    }

    @Override
    public boolean tick() {

        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName() + " is does not feel like stealing");
            return false;
        }

        List<Mob> mobs = mob.getRoom().getMobs();

        int randomIndex = (int) (Math.random() * mobs.size());

        Mob victim = mobs.get(randomIndex);

        if (victim == mob) {
            LOGGER.debug("Will not steal from self");

            if (randomIndex > 0) {
                randomIndex--;
            } else {
                randomIndex++;
                if (randomIndex >= mobs.size()) {
                    return false;
                }
            }
            victim = mobs.get(randomIndex);
        }

        SomeMoney money = new Money(Money.COPPER, 5);

        if (!victim.getInventory().getPurse().remove(money)) {
            LOGGER.debug("No money to steal");
            return false;
        }

        mob.getInventory().add(money);

        return false;
    }

}
