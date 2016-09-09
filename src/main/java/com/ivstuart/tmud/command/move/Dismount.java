/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.move;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.constants.SkillNames;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;

public class Dismount extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        Mob ride = mob.getMount();

        if (ride == null) {
            mob.out("You are already dismounted.");
            return;
        }

        mob.out("You dismount from your ride " + ride.getName());
        mob.setMount(null);

    }
}