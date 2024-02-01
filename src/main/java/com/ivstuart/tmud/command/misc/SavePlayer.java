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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @author stuarti
 */
public class SavePlayer extends BaseCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(Mob mob_, String input) {

        mob_.out("Saving your player.");

        Player player = mob_.getPlayer();

        player.getData().setPlayingTime();
        player.getData().setLoginTime(System.currentTimeMillis());

        // Save character first
        try {
            MudIO.getInstance().save(player, player.getSaveDirectory(), mob_.getId() + ".sav");
        } catch (IOException e) {
            LOGGER.error("Problem saving character", e);
            mob_.out("Problem saving character!");
            return;
        }

    }

}
