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

/*
 * Created on 17-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.misc;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.utils.MudIO;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @author stuarti
 * 
 */
public class Quit extends BaseCommand {

	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void execute(Mob mob, String input) {

		if (mob.getFight().isEngaged()) {
            mob.out("You can not quit while being attacked!");
            return;
		}

        mob.out("Thank you for playing");

		Player player = mob.getPlayer();

        player.getData().setPlayingTime();

        mob.setRoomId(mob.getRoom().getId());

		// Save character first
		try {

            MudIO.getInstance().save(player, player.getSaveDirectory(), mob.getId() + ".sav");

			// The following GSON does not work to serialise the player, do not use it.
			// GsonIO gio = new GsonIO();
			// gio.save(player, player.getName() + ".sav");
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
