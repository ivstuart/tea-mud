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

package com.ivstuart.tmud.command.misc;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.utils.MudIO;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @author stuarti
 */
public class ForcedQuit extends BaseCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(Mob mob, String input) {

        Player player = mob.getPlayer();

        if (player == null) {
            LOGGER.warn("Force quit for null player already quit");
            return;
        }

        if (player.getConnection().isConnected()) {
            mob.out("Thank you for playing you have been forced to quit");
        } else {
            LOGGER.warn("Force quit for player already disconnected");
            // TODO when disconnected client need to allow save and quit too. Remove return
            // NOTE When this was removed before it lead to corrupted file state!
            return;
        }

        mob.out("Thank you for playing");

        player.getData().setPlayingTime();

        // Save character first
        try {
            MudIO.getInstance().save(player, player.getSaveDirectory(), mob.getId() + ".sav");
        } catch (IOException e) {
            LOGGER.error("Problem saving character", e);
            mob.out("Problem saving character!");
            return;
        }

        // getCharacter().getLocation().save();
        mob.getRoom().remove(mob);
        mob.getFight().stopFighting();
        mob.getFight().clear();

        // Remove from World (Delay if recently been flagged)
        player.disconnect();
        World.removePlayer(player);

    }

}
