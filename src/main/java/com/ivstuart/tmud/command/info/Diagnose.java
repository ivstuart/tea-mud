/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
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
