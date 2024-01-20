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
import com.ivstuart.tmud.state.Mob;

public class Disengage extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        if (!mob.getFight().isFighting()) {
            mob.out("Someone much be attacked by you in order to disengage");
            return;
        }

        Mob target = mob.getFight().getTarget();

        if (target != null) {
            mob.out("You disengage from fighting a " + target.getName());
        }

        mob.getFight().stopFighting();

    }

}