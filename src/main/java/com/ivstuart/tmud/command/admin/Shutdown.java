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
