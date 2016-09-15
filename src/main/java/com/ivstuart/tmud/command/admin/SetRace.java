/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.utils.StringUtil;

public class SetRace extends AdminCommand {

    @Override
    public void execute(Mob mob_, String input) {

        super.execute(mob_, input);

        String element[] = input.split(" ");

        Mob mob = mob_;

        if (element.length == 2) {
            mob = mob_.getRoom().getMob(element[0]);
        }

        int raceId = Integer.parseInt(StringUtil.getLastWord(input));

        mob.setRaceId(raceId);

        mob_.out("You set the race of " + mob.getName() + " to race " + mob.getRace().getName());


    }
}
