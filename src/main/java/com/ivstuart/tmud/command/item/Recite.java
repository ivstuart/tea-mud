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
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Scroll;
import com.ivstuart.tmud.utils.StringUtil;

public class Recite extends BaseCommand {

	/**
	 * @param input
	 * @return
	 */


	@Override
	public void execute(Mob mob, String input) {

		Item item = mob.getInventory().get(StringUtil.getFirstWord(input));

		if (item == null) {
			mob.out("You can not find a "+input+" to recite");
			return;
		}

		if (!item.isRecitable()) {
			mob.out("You can not recite a "+item.getName() );
			return;
		}

		Scroll scroll = null;
		if (item instanceof Scroll) {
			scroll = (Scroll)item;
		}

		if (scroll == null) {
			mob.out("The item "+item.getName()+" is not a scroll" );
			return;
		}

		scroll.recite(mob, input);

		mob.getInventory().getItems().remove(scroll);

		mob.out("The scroll crumbles to dust the energies used up");

	}


}
