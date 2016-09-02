/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.communication;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Say extends BaseCommand {

	// say | shout | holler <string>

	@Override
	public void execute(Mob mob, String input) {


        mob.getRoom().out(mob.getId() + " says, \"" + input + "\"");
	}

}
