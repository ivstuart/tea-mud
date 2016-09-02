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
import com.ivstuart.tmud.state.World;
import com.ivstuart.tmud.utils.MudIO;
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
	public void execute(Mob mob_, String input) {

		if (mob_.getFight().isEngaged()) {
			mob_.out("You can not move while being attacked!");
			return;
		}

		mob_.out("Thankyou for playing");

		Player player = mob_.getPlayer();

        player.getData().setPlayingTime();

		// Save character first
		try {

			MudIO.getInstance().save(player, mob_.getId() + ".sav");

			// FIXME more issues then it solves.
			// GsonIO gio = new GsonIO();
			// gio.save(player, player.getName() + ".sav");
		} catch (IOException e) {
			LOGGER.error("Problem saving character", e);
			mob_.out("Problem saving character!");
			return;
		}

		// getCharacter().getLocation().save();
		mob_.getRoom().remove(mob_);
		mob_.getFight().stopFighting();
		mob_.getFight().clear();

		// Remove from World (Delay if recently been flagged)
		player.disconnect();
		World.removePlayer(player);

	}

}
