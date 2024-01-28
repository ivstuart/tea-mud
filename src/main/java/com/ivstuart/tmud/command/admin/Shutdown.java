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

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.command.misc.Quit;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.Clans;
import com.ivstuart.tmud.world.MudStats;
import com.ivstuart.tmud.world.PostalSystem;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Shutdown extends AdminCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(Mob mob, String input) {

        World.out("Mudserver shutdown started you will be kicked off from playing", true);
        World.out("Mudserver shutdown started you will be kicked off from playing", false);

        World.shutdownAuctions();
        World.shutdown();
        PostalSystem.shutdown();
        Clans.shutdown();
        MudStats.shutdown();


        // Quit all players so that they saved.
        Object[] players = World.getPlayers().toArray();
        for (Object player : players) {
            String playerName = (String) player;
            Mob playerMob = World.getMob(playerName.toLowerCase());
            if (playerMob != null) {
                CommandProvider.getCommand(Quit.class).execute(playerMob, null);
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOGGER.error("Interrupted while shutting down:", e);
        }

        System.exit(0);
    }
}
