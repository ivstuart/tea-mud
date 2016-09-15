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
package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.Corpse;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Prop;
import com.ivstuart.tmud.utils.MudArrayList;
import com.ivstuart.tmud.utils.StringUtil;

/**
 * @author stuarti
 *
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sacrifice extends BaseCommand {

	/**
	 * @param input
	 * @return
	 */
	private boolean checkSacCash(Mob mob, String input) {
		SomeMoney sm = mob.getRoom().getInventory().removeCoins(input);

		if (sm != null) {
			mob.out("You sacrifice " + sm);
			return true;
		}

		return false;
	}

	@Override
	public void execute(Mob mob, String input) {

		if (checkSacCash(mob, input)) {
			return;
		}

		String lastWord = StringUtil.getLastWord(input);
		Prop prop = mob.getRoom().getProps().get(lastWord);

		if (prop != null) {
			if (prop instanceof Corpse) {
				Corpse corpse = (Corpse) prop;
				mob.getRoom().remove(corpse);
				mob.out("You sacrifice an " + corpse.getName());
				return;
			}
		}

		MudArrayList<Item> items = mob.getRoom().getInventory().getItems();

		if (items == null) {
			mob.out(input + " is not here to sacrifice!");
			return;
		}

		Item anItem = items.remove(input);

		if (anItem == null) {
			mob.out("Can not sacrifice " + input + " it is not here!");
			return;
		}

		mob.out("You sacrifice an " + anItem);
	}

}
