/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Hide implements Command {

	/*
	 * 2ndary action?
	 */
	@Override
	public void execute(Mob mob, String input) {

		mob.out("todo hide");
		
		// TODO can hide self
		//          hide objects
		//          hide exits or props only if they where hidden in the first place and made visible.
		
		// Do "search" in collaboration with this command.
	}

}
