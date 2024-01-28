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

package com.ivstuart.tmud.fighting.action;

import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.ivstuart.tmud.person.movement.MoveManager.random;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Flee extends FightAction {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * @param character
     */
    public Flee(Mob character) {
        super(character, null);
    }

    /*
     * (non-Javadoc)
     *
     * @see fighting.FightAction#begin()
     */
    @Override
    public void begin() {

        out("You start looking for an opening to flee");
        duration(1);

    }

    /*
     * (non-Javadoc)
     *
     * @see fighting.FightAction#changed()
     */
    @Override
    public void changed() {

        out("You change from fleeing from someone");
        // duration(2000);

    }

    /*
     * (non-Javadoc)
     *
     * @see fighting.FightAction#destory()
     */
    @Override
    public void destroy() {


    }

    /*
     * (non-Javadoc)
     *
     * @see fighting.FightAction#ended()
     */
    @Override
    public void ended() {

        out("You end from fleeing");
        duration(5);
    }

    private void fail() {
        out("You fail to flee!");
    }

    /*
     * (non-Javadoc)
     *
     * @see fighting.FightAction#happen()
     */
    @Override
    public void happen() {
        // Consider if flee chance should be based on attack offensive v defenders defence scores.

        int successChance = 50;
        if (getSelf().getMobStatus().isGroundFighting()) {
            LOGGER.debug("Harder to flee when ground fighting better to try to stand first");
            successChance = 30;
        }

        if (isSuccess(successChance)) {
            success();
        } else {
            fail();
        }
        duration(3);

    }

    @Override
    public boolean isMeleeEnabled() {
        return false;
    }

    private boolean isSuccess(int percentage) {
        if (percentage > 95) {
            percentage = 95;
        }
        return Math.random() * 100 < percentage;
    }

    private void success() {
        /* 1st room to flee into */
        Exit exit = random(getSelf());
        if (exit == null) {
            out("You can't get away!");

        } else {
            out("Attempting to flee in " + exit.getId() + " direction.");
            out("You succeed in fleeing a room!");
        }
    }
}
