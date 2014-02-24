/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.communication;

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
public class Bug implements Command {

	/** 
	 * Store these as emails or reports to separate location
	 */
	@Override
	public void execute(Mob mob, String input) {

		// To all for now, will need to remove this when alignment and factions introduced.
		for (String player : World.getPlayers()) {
			World.getPlayer(player).out("BUG:"+input);
		}
	}

}
