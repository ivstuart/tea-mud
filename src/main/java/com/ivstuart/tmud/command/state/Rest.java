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
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.items.Prop;
import com.ivstuart.tmud.state.places.RoomEnum;
import com.ivstuart.tmud.utils.StringUtil;

import static com.ivstuart.tmud.common.MobState.REST;
import static com.ivstuart.tmud.common.MobState.REST_ON;

public class Rest extends BaseCommand {

    @Override
    public void execute(Mob mob_, String input_) {

        if (mob_.getState() == REST) {
            mob_.out("You are already resting!");
            return;
        }

        if (mob_.isRiding()) {
            mob_.out("You need to dismount in order to go to rest");
            return;
        }

        if (mob_.getFight().isEngaged()) {
            mob_.out("You can not rest you are fighting");
            return;
        }

        // Check allowed to change state
        if (mob_.getRoom().hasFlag(RoomEnum.AIR)) {
            mob_.out("You can not rest here you must continue to fly");
            return;
        }

        if (mob_.getRoom().hasFlag(RoomEnum.WATER)) {
            mob_.out("You can not rest here you must continue to swim");
            return;
        }
        // Change state and notify mob and room

        if (checkRestOn(mob_, input_)) return;

        mob_.out("You stop " + mob_.getState().getDesc() + " and rest.");

        mob_.setState(REST);

    }

    private boolean checkRestOn(Mob mob_, String input_) {
        String target = StringUtil.getLastWord(input_);

        if (target != null && !target.isEmpty()) {
            Prop prop = mob_.getRoom().getProps().get(target);

            if (prop == null) {
                mob_.out("There is no " + target + " to rest on here.");
                return true;
            }

            if (!prop.isSittable()) {
                mob_.out("You can not rest on a " + target);
                return true;
            }

            mob_.out("You rest on a " + prop.getBrief());

            mob_.setState(REST_ON);
            return true;
        }
        return false;
    }
}
