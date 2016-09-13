/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Possess extends AdminCommand {

	/**
	 * Make a target mob the mob you control
	 *  technical note to park your original mob somewhere safe in the meantime.
	 */
	@Override
	public void execute(Mob mob, String input) {

		super.execute(mob,input);

		Mob target = mob.getRoom().getMobs().get(input);

		if (target == null) {
			mob.out("You see no " + input + " here to possess.");
			return;
		}

		if (target.isPlayer()) {
			mob.out("You can not possess a player");
			return;
		}

		mob.out("You possess a " + target.getName());
		mob.getPlayer().setPossess(target);
		target.setPossessed(mob);
	}

}
