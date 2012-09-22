/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.command.item.Wear;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Equipment implements Command {

	@Override
	public void execute(Mob mob, String input) {
		if (input.length() > 0) {
			CommandProvider.getCommand(Wear.class).execute(mob, input);
			return;
		}
		mob.out(mob.getEquipment().toString());
	}

}
