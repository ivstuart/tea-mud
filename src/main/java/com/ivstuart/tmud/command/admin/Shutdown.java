/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.command.misc.Quit;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.Clans;
import com.ivstuart.tmud.world.MudStats;
import com.ivstuart.tmud.world.PostalSystem;
import com.ivstuart.tmud.world.World;

public class Shutdown extends AdminCommand {

    @Override
    public void execute(Mob mob, String input) {

        World.out("Mudserver shutdown started you will be kicked off from playing",true);
        World.out("Mudserver shutdown started you will be kicked off from playing",false);

        World.shutdownAuctions();
        World.shutdown();
        PostalSystem.shutdown();
        Clans.shutdown();
        MudStats.shutdown();


        // Quit all players so that they saved.
        Object[] players = World.getPlayers().toArray();
        for (int index = 0; index < players.length; index++) {
            String playerName = (String) players[index];
            Mob playerMob = World.getMob(playerName.toLowerCase());
            if (playerMob != null) {
                CommandProvider.getCommand(Quit.class).execute(playerMob, null);
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
}
