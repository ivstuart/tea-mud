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
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.constants.SkillNames;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Corpse;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Prop;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Diagnose extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		Prop corpse = mob.getRoom().getProps().get(input);

		if (corpse == null) {
			mob.out("There is no " + input + " to investigate");
			return;
		}

		if (!(corpse instanceof Corpse)) {
			mob.out("That item is not a corpse");
			return;
		}

		Ability ability = mob.getLearned().getAbility(SkillNames.INVESTIGATE);

		if (ability.isNull()) {
			mob.out("You have no skill to investigate");
			return;
		}

		Corpse corpse1 = (Corpse) corpse;

		if (ability.isSuccessful(mob)) {
			mob.out(new Msg(mob, ("<S-You/NAME> successfully investigated.")));

			mob.out(corpse1.investigation());

		} else {
			mob.out(new Msg(mob, ("<S-You/NAME> failed to investigate.")));
		}

	}

}
