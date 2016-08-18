/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.misc;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.World;
import org.apache.log4j.Logger;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Title extends BaseCommand {

	private static final Logger LOGGER = Logger.getLogger(Title.class);

	@Override
	public void execute(Mob mob, String input) {

		Player player = mob.getPlayer();

		if (player == null) {
			LOGGER.warn("A none player called Who command");
			return;
		}

		player.getData().setTitle(input);
		player.out("Setting your title to " + input);
	}

}
