/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.admin;

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
public class Snoop implements Command {

	/**
	 * View all input from player as if in the same room.
	 */
	@Override
	public void execute(Mob mob, String input) {

		if(!mob.isAdmin()) {
			mob.out("Admin only");
			// return;
		}
		
		Player player = World.getPlayer(input);

		if (player == null) {
			mob.out("Can not find player "+input+" to snoop on");
			return;
		}
		
		if (input.equals("off")) {
			mob.out("You stop snooping on player "+player.getName());
			player.setSnooper(null);
		}
		else {
			mob.out("You start snooping on player "+player.getName()+" command with arg off to switch off snooping");
			player.setSnooper(mob);
		}
	}

}
