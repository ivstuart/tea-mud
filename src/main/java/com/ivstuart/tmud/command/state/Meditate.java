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
import com.ivstuart.tmud.state.places.RoomEnum;

import static com.ivstuart.tmud.common.MobState.MEDITATE;

public class Meditate extends BaseCommand {

    @Override
    public void execute(Mob mob_, String input_) {

        if (mob_.getState() == MEDITATE) {
            mob_.out("You are already meditating!");
            return;
        }

        if (mob_.isRiding()) {
            mob_.out("You need to dismount in order to go to meditate");
            return;
        }

        if (mob_.getFight().isEngaged()) {
            mob_.out("You can not meditate you are fighting");
            return;
        }

        // Check allowed to change state
        if (mob_.getRoom().hasFlag(RoomEnum.AIR) && !mob_.getMobAffects().hasAffect("levitate")) {
            mob_.out("You must continue to fly here");
            return;
        }
        // Change state and notify mob and room

        mob_.out("You stop " + mob_.getState().getDesc() + " and meditate.");

        mob_.setState(MEDITATE);
    }

}
