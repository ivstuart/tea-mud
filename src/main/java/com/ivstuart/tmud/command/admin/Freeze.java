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
public class Freeze extends AdminCommand {

	/**
	 * Freeze input from player to their mob.
	 * Lasts until freeze is removed. 
	 * Allowed to quit.
	 */
	@Override
	public void execute(Mob mob, String input) {

		super.execute(mob,input);
		
		Player player = World.getPlayer(input);
		
		if (player == null) {
			mob.out("No player by the name of "+input+" is currently playing");
			mob.out("Use ban if you have their name instead");
			return;
		}
		
		player.getMob().getMobStatus().setFrozen(8000);
		
		mob.out("Your freeze player "+player.getName());
		player.out("You have been frozen by an admin, you can only quit");
	}

}
