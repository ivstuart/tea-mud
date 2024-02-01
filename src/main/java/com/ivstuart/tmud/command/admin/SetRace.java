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
import com.ivstuart.tmud.utils.StringUtil;

public class SetRace extends AdminCommand {

    @Override
    public void execute(Mob mob_, String input) {

        super.execute(mob_, input);

        String[] element = input.split(" ");

        Mob mob = mob_;

        if (element.length == 2) {
            mob = mob_.getRoom().getMob(element[0]);
        }

        int raceId = Integer.parseInt(StringUtil.getLastWord(input));

        mob.getMobBodyStats().setRaceId(raceId);

        mob_.out("You set the race of " + mob.getName() + " to race " + mob.getRace().getName());


    }
}
