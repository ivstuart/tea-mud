/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Unlearn extends BaseCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {

		// check do not already have skill

		if (!mob.getLearned().hasLearned(input)) {
			mob.out("You do not know " + input + " to unlearn");
			return;
		}

		Ability ab = mob.getLearned().remove(input);

		mob.out("You unlearn " + ab);

		// Assume give back a learn if you unlearn something.
		mob.getPlayer().getData().addLearn();

	}

}
