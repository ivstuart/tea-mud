/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 */
public class ExitInfo extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		StringBuilder sb = new StringBuilder();

		for (Exit exit : mob.getRoom().getExits()) {
			if (!exit.isHidden() || mob.hasDetectHidden()) {
				sb.append(exit.look()).append(" ");
			}

		}

		mob.out("  $K[Exits: " + sb + "]$J");

	}

}
