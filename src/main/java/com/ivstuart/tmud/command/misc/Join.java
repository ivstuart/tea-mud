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
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.ProfessionMaster;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Join extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		ProfessionMaster professionMaster = mob.getRoom().getProfessionMaster();

		if (professionMaster == null) {
			mob.out("There is no one here to join a profession with");
			return;
		}

		if (mob.getPlayer().getProfession() != null) {
			mob.out("You already have a profession");
			return;
		}

		// 88 gold to join
		SomeMoney cash = mob.getInventory().removeCoins("88 gold");

		if (cash == null || cash.getValue() < 8800) {
			mob.out("You must have 88 gold coins to join");
			return;
		}

		mob.out("You join the "+professionMaster.getProf()+" profession, welcome.");
		mob.getPlayer().setProfession(professionMaster.getProf());


	}

}
