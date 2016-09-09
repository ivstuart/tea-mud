/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
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
