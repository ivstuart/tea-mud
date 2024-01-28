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
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.RoomEnum;

import static com.ivstuart.tmud.common.MobState.FLYING;

public class Fly extends BaseCommand {

    @Override
    public void execute(Mob mob_, String input_) {

        // Check current state
        if (mob_.isFlying()) {
            mob_.out("You are already flying!");
            return;
        }

        if (mob_.getRoom().hasFlag(RoomEnum.NARROW)) {
            mob_.out("You can not fly here inside a tunnel");
            return;
        }

        if (!mob_.getRace().isFly()) {
            // Check allowed to change state
            if (!mob_.getMobAffects().hasAffect("levitate")) {
                mob_.out("You do not have that spell active");
                return;
            }
        }

        // Change state and notify mob and room
        mob_.getRoom().out(new Msg(mob_, "<S-NAME> start flying"));
        mob_.setState(FLYING);

    }

}
