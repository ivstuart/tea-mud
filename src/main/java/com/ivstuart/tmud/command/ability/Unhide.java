/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 */
public class Unhide implements Command {

	@Override
	public void execute(Mob mob, String input) {

		if (mob.getMobStatus().isHidden()) {
			mob.getMobStatus().setHidden(0);
			mob.out("You stop hidding."); 
		}
		else {
			mob.out("You were not hidding in the first place");
		}
		
	}

}
