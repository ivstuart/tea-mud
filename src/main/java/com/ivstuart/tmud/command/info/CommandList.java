/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.*;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommandList extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {
		// TODO Auto-generated method stub
		for (Object command : CommandProvider.getCommands()) {
			mob.out(command.toString());
		}
	}

}
