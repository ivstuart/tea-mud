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
public class Steal implements Command {

	/*
	 * 2ndary action?
	 */
	@Override
	public void execute(Mob mob, String input) {

		if (!mob.getLearned().hasLearned("steal")) {
			mob.out("You have no knowledge of steal");
			return;
		}

		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to steal from!");
			return;
		}

		mob.out("steal todo");
	}

}
