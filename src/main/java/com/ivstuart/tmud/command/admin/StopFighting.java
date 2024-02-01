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

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.world.WorldTime;

public class StopFighting extends AdminCommand {

    @Override
    public void execute(Mob mob, String input) {

        super.execute(mob, input);

        if (input.equalsIgnoreCase("world")) {
            for (Mob fighter : WorldTime.getFighting()) {
                fighter.getFight().stopFighting();
            }
            WorldTime.getFighting().clear(); // defense coding.
            mob.out("Admin stop fighting for the world");
            return;
        }

        mob.out("Admin stop fighting for mob target and mob");

        mob.getTargetFight().stopFighting();
        mob.getFight().stopFighting();

    }
}
