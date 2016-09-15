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
package com.ivstuart.tmud.command.misc;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.ProfessionMaster;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Leave extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		ProfessionMaster professionMaster = mob.getRoom().getProfessionMaster();

		if (professionMaster == null) {
			mob.out("There is no one here to leave a profession with");
			return;
		}

		if (mob.getPlayer().getProfession() == null) {
			mob.out("You already have no profession");
			return;
		}

		if (!professionMaster.getProf().equals(mob.getPlayer().getProfession())) {
			mob.out("This is the wrong profession master to leave from");
			return;
		}

		mob.getPlayer().setProfession(null);


	}

}
