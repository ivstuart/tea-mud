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
public class Circle implements Command {

	/*
	 * 2ndary action?
	 */
	@Override
	public void execute(Mob mob, String input) {

		if (!mob.getLearned().hasLearned("circle")) {
			mob.out("You have no knowledge of circle");
			return;
		}

		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to circle!");
			return;
		}

		// TODO need to check if another thief is circling at the same time
		// they will bump into each other and lose that circle in that event.

		mob.out("circle todo see bash");
	}

}
