/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.misc;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Title extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {
		// TODO Auto-generated method stub

	}

	public void execute(Player mob, String input) {

		// mob.getStats().getMiscStats().setTitle(input);
		mob.getData().setTitle(input);
		mob.out("Setting your title to " + input);
	}

}
