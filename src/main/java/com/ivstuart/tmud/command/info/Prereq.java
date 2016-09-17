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
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.BaseSkill;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.World;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Prereq extends BaseCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {

		BaseSkill skill = World.getSkill(input);

		if (skill == null) {
			skill = World.getSpell(input);
		}

		if (skill == null) {
			mob.out("The " + input + " is not available in this world");
			return;

		}

		String ability = skill.getPrereq();

		if (ability == null) {
			mob.out("The skill " + skill.getName() + " has no prerequisite");
		}

		if (mob.getLearned().hasPrereq(ability)) {
			mob.out("You have the prerequisite " + ability + " ");
			return;
		} else {
			mob.out("You do not have  the prerequisite " + ability + " ");
		}

	}

}
