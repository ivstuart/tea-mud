/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 */
public class Unhide extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		if (mob.isHidden()) {
			mob.setHidden(false);
			mob.out("You stop hiding.");
		}
		else {
			mob.out("You were not hiding in the first place");
		}
		
	}

}
