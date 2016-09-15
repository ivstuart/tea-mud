/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class At extends AdminCommand {

	/**
	 * At allows you to pass a command line to another player
	 */
	@Override
	public void execute(Mob mob, String input) {

		super.execute(mob,input);

		// mob command args
		String[] args = input.split(" ", 3);
		
		if (args.length < 2) {
			mob.out("Usage: mob command args");
			return;
		}

		Mob target = mob.getRoom().getMob(args[0]);

		if (target == null) {
			mob.out(input + " is not here to at!");
			return;
		}

		mob.out("You make mob "+target.getName()+" "+args[1]+" with "+args[2]);
		CommandProvider.getCommandByString(args[1]).execute(target, args[2]);

		
	}

}
