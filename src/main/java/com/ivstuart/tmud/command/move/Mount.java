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

package com.ivstuart.tmud.command.move;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.constants.SkillNames;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.MobEnum;
import com.ivstuart.tmud.world.WorldTime;

public class Mount extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        Mob ride = mob.getRoom().getMob(input);

        if (ride == null) {
            mob.out("There is no one here to mount");
            return;
        }

        if (!ride.hasMobEnum(MobEnum.MOUNT)) {
            mob.out("That creature can not be ridden");
            return;
        }

        Ability ability = mob.getLearned().getAbility(SkillNames.RIDING);

        if (ability.isNull()) {
            mob.out("You have no skill with riding");
            return;
        }

        mob.out("You mount a " + ride.getName());
        mob.setMount(ride);

        WorldTime.addTickable(ride);

    }
}