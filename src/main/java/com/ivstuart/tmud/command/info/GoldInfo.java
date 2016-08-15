/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.person.carried.Inventory;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GoldInfo extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		mob.out("You are carrying:\n");

		Inventory inve = mob.getInventory();

		if (inve == null) {
			mob.out("Not a saussage!");
			return;
		}

		mob.out(mob.getInventory().getPurseString());
	}

}
