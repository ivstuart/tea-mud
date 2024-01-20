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

package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.state.Mob;

public class Kill extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        if (mob.getRoom().isPeaceful()) {
            mob.out("You can not be aggressive in this room");
            return;
        }

        Mob target = mob.getRoom().getMob(input);

        if (target == null) {
            mob.out(input + " is not here to kill!");
            return;
        }

        //  decide if an attack when can not see them
        if (target.isInvisible() && !mob.hasDetectInvisible()) {
            mob.out(input + " is not seen here to kill!");
            return;
        }

        if (target.isHidden() && !mob.hasDetectHidden()) {
            mob.out(input + " is not seen here to kill!");
            return;
        }

        Fight.startCombat(mob, target);

    }

}