/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

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
public class Who implements Command {

	private static final Logger LOGGER = Logger.getLogger(Who.class);

	@Override
	public void execute(Mob mob, String input) {

		mob.out("$~");

		for (String playerName : World.getPlayers()) {
			Player player = World.getPlayer(playerName);
			if (player == null) {
				continue;
			}
			String line = playerName + player.getData().getTitle();
			mob.out(line);

			LOGGER.debug("line");
		}

		mob.out("$~");
	}

}
