/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.move;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.constants.SkillNames;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.WorldTime;

public class Mount extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        Mob ride = mob.getRoom().getMob(input);

        if (ride == null) {
            mob.out("There is no one here to mount");
            return;
        }

        if (!ride.isRidable()) {
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