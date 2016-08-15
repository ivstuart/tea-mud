/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import org.apache.log4j.Logger;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.World;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Who extends BaseCommand {

	private static final Logger LOGGER = Logger.getLogger(Who.class);

	@Override
	public void execute(Mob mob, String input) {

		if (mob.getPlayer().getData().getLevel() < 5) {
			mob.out("You can not use this command who until level 5");
			return;
		}

		mob.out("$K~$J");

		LOGGER.debug("Who debugging");

		for (String playerName : World.getPlayers()) {

			LOGGER.debug("Player name is "+playerName);

			Player player = World.getPlayer(playerName.toLowerCase());
			if (player == null) {
				LOGGER.debug("Player null in world is "+playerName);
				continue;
			}
			String title = playerName + player.getData().getTitle();

			mob.out(title);
		}

		mob.out("$K~$J");
	}

}
