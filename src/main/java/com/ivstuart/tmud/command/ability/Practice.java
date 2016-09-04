/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Teacher;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Practice extends BaseCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {

		// check do not already have skill

		if (!mob.getLearned().hasLearned(input)) {
			mob.out("You need to learn " + input + " in order to practice it");
			return;
		}

		// check has at least one learn available to spend
		if (mob.getPlayer().getData().getPracs() < 1) {
			mob.out("You have no pracs to practice with " + input);
			return;
		}

		// check teacher in the room
		Teacher teacher = mob.getRoom().getFirstTeacher();

		if (null == teacher) {
			mob.out("No teacher here to practice from");
			return;
		}
		String ability = input;

		if (!teacher.teachesEverything()) {

			ability = teacher.getAbility(input);

			if (null == ability) {
				mob.out("Teacher does not teach " + input);
				return;
			}
		}

		// Check skill or spell is available in this world

		Ability theAbility = mob.getLearned().getAbility(ability);

        if (theAbility.isNull()) {
            mob.out("There is no ability " + input + " to learn");
            return;
        }

		if (theAbility.practice(mob.getPlayer())) {
			mob.out("You practice " + theAbility);
			mob.getPlayer().getData().prac();
			return;
		}

		mob.out("You can not practice " + theAbility + " anymore!");

	}

}
