package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.command.misc.Quit;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.World;
import com.ivstuart.tmud.state.WorldTime;

public class Shutdown extends AdminCommand {

    @Override
    public void execute(Mob mob, String input) {

        World.out("Mudserver shutdown started you will be kicked off from playing",true);
        World.out("Mudserver shutdown started you will be kicked off from playing",false);

        World.shutdown();

        // Quit all players so that they saved.
        for (String playerName : World.getPlayers()) {
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
