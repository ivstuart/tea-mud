/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.constants.Profession;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.BaseSkill;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Teacher;
import com.ivstuart.tmud.world.World;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Learn extends BaseCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {

		// check teacher in the room
		Teacher teacher = mob.getRoom().getFirstTeacher();

		if (null == teacher) {
			mob.out("No teacher here to learn from");
			return;
		}

		if (input.length() == 0) {
			// Just show what the teacher can teach
			mob.out("This teacher here teaches:\n" + teacher.displayAbilities());
			return;
		}

		// check do not already have skill

		if (mob.getLearned().hasLearned(input)) {
			mob.out("You already know " + input);
			return;
		}

		// check has at least one learn available to spend
		if (mob.getPlayer().getData().getLearns() < 1) {
			mob.out("You have no learns to learn " + input);
			return;
		}

		String ability = null;

		if (teacher.teachesEverything()) {
			ability = input;
		} else {
			ability = teacher.getAbility(input);
		}

		if (null == ability) {
			mob.out("Teacher does not teach " + input);
			return;
		}

		// Check skill or spell is available in this world

		BaseSkill theAbility = World.getAbility(ability);

		if (theAbility == null) {
			mob.out("That ability is not known to this world");
			return;
		}

		String prof = theAbility.getProf();

		if (prof != null) {
			Profession profession = mob.getPlayer().getProfession();

			if (!profession.equals(Profession.valueOf(prof))) {
				mob.out("You require profession "+prof+" to learn that ability");
				return;
			}
		}

		// Check you have learned and praced the prereq to talented.

		String prereq = theAbility.getPrereq();

		if (null != prereq) {
			if (!mob.getLearned().hasPrereq(prereq)) {
				mob.out("You have not gained enough skill in " + prereq
						+ " to learn" + theAbility);
				return;
			}
		}

		mob.getLearned().add(new Ability(theAbility.getId()));

		mob.getPlayer().getData().learn();

		mob.out("You learn " + theAbility.getId());

	}

}
