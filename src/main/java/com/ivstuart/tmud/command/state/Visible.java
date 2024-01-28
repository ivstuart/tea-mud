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

package com.ivstuart.tmud.command.state;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.statistics.affects.Affect;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.common.MobState.STAND;
import static com.ivstuart.tmud.constants.SpellNames.INVISIBILITY;

public class Visible extends BaseCommand {

    @Override
    public void execute(Mob mob_, String input_) {

        // Check current state
        if (mob_.getState().isSleeping()) {
            mob_.out("You are sleeping wake up to vis and invis!");
            return;
        }

        Affect invisAffect = mob_.getMobAffects().getAffect(INVISIBILITY);

        if (invisAffect == null) {
            mob_.out("That spell is not active!");
            return;
        }

        // Check allowed to change state
        if (mob_.isInvisible()) {
            mob_.out("You turn yourself visible");
            mob_.setInvisible(false);
            return;
        }

        // Change state and notify mob and room

        mob_.out("You turn yourself invisible");

        mob_.setState(STAND);
        mob_.setInvisible(true);

        // Note sleep to wake you will also stand
    }

}
