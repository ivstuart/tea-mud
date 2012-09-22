/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.misc;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.person.config.*;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PickLocks implements Command {

	// TODO
	@Override
	public void execute(Mob mob, String input) {

		mob.out("TODO compact");
		mob.getPlayer().getConfig().getConfigData().toggle(ConfigData.COMBINE);
	}

	public String getHelp() {

		return "This command toggles the extra blank line output to your screen after each"
				+ "command. Useful if you have a small display.";
	}

}
