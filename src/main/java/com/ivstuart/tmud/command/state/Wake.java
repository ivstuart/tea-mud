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

/*
 * Created on 12-Nov-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.state;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.statistics.affects.SleepAffect;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.common.MobState.*;

public class Wake extends BaseCommand {

    @Override
    public void execute(Mob mob_, String input_) {

        if (!input_.isEmpty()) {
            Mob mobToWake = mob_.getRoom().getMob(input_);

            if (mobToWake == null) {
                mob_.out("Can not see " + input_ + " to wake");
                return;
            }

            if (mobToWake.getState() != SLEEP && mobToWake.getState() != SLEEP_ON) {
                mob_.out(mob_.getName() + " is already awake!");
                return;
            }

            mobToWake.setState(STAND);
            mobToWake.out("You are woken by " + mob_.getName());
            mob_.out("You wake " + mobToWake.getName());
            return;

        }

        // Check current state
        if (mob_.getState() != SLEEP && mob_.getState() != SLEEP_ON) {
            mob_.out("You are already awake!");
            return;
        }

        // Check allowed to change state
        SleepAffect sleepingSpell = (SleepAffect) mob_.getMobAffects().getAffect("sleep");

        if (sleepingSpell != null) {
            mob_.out("You are under the effects of a sleep spell!");
            return;
        }
        // Change state and notify mob and room

        mob_.out("You wake");

        mob_.setState(STAND);

        // Note sleep to wake you will also stand
    }

}
