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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @author stuarti
 * 
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
			MudIO.getInstance().save(player, mob_.getId() + ".sav");
		} catch (IOException e) {
			LOGGER.error("Problem saving character", e);
			mob_.out("Problem saving character!");
			return;
		}

	}

}
