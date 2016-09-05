package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.state.Mob;

/**
 * Created by Ivan on 05/09/2016.
 */
public class GiveWarpoints extends AdminCommand {

    @Override
    public void execute(Mob mob, String input) {

        mob.getPlayer().getData().incrementWarpoints(Integer.parseInt(input));

        mob.out("You gain " + input + " war points");

    }
}
