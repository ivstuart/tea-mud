/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Help implements Command {

	@Override
	public void execute(Mob mob, String input) {

		// Have a help

		// Have a help <topic|specific>

		// Have a help <topic>

		// i.e. help commands
		// help
		// help skills
		// help skills
		// help bash
		mob.out("Todo");
	}

}
