/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
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
public class ForcedQuit extends BaseCommand {

	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void execute(Mob mob_, String input) {

		Player player = mob_.getPlayer();

		if (player == null) {
			LOGGER.warn("Force quit for null player already quit");
			return;
		}

		if (player.getConnection().isConnected()) {
			mob_.out("Thank you for playing you have been forced to quit");
			mob_.out("If you did not just login on a second client please login in again and update your password.");
		}

        player.getData().setPlayingTime();

		mob_.setRoomId(mob_.getRoom().getId());

		mob_.getRoom().remove(mob_);
		mob_.getFight().stopFighting();
		mob_.getFight().clear();

		player.disconnect();
		World.removePlayer(player);

        try {
            MudIO.getInstance().save(player, mob_.getId() + ".sav");
        } catch (IOException e) {
            LOGGER.error("Problem saving character", e);
            return;
        }

	}

}
