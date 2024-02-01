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

package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.state.mobs.Mob;

public class Assist extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        Mob target = null;

        if (input == null || input.isEmpty()) {
            for (Mob aMob : mob.getPlayer().getGroup()) {
                if (!aMob.getFight().isFighting()) {
                    continue;
                }
                if (aMob == mob) {
                    continue;
                }
                target = aMob;
                break;
            }
        } else {
            target = mob.getRoom().getMob(input);
        }

        if (target == null) {
            mob.out("No one here to assist!");
            return;
        }

        Mob targetTarget = target.getFight().getTarget();

        if (targetTarget == null) {
            mob.out("Your colleague is not fighting anyone");
            return;
        }

        mob.getFight().changeTarget(targetTarget);

        Fight.startCombat(mob, targetTarget);

        mob.out(new Msg(mob, targetTarget, ("<S-You/NAME> assist " + target.getName() + " by attacking <T-NAME>")));

    }

}