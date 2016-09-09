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
import com.ivstuart.tmud.state.Torch;
import com.ivstuart.tmud.world.WorldTime;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Lite extends BaseCommand {

	@Override
	public void execute(Mob mob_, String input_) {

		if (mob_.getInventory().hasLighter() == false) {
			mob_.out("You have nothing to lite the " + input_ + " with.");
			return;
		}

		Item item = mob_.getInventory().get(input_);

		if (item == null) {
			item = (Item) mob_.getEquipment().get(input_);
		}

		if (item == null) {
			mob_.out("You are not carrying a " + input_ + " to lite.");
			return;
		}

		if (item instanceof Torch) {
			Torch torch = (Torch) item;

			if (torch.isLit() == true) {
				mob_.out("That is already alight!");
				return;
			}

			if (torch.getFuel() == 0) {
				mob_.out("That has already burnt out!");
				return;
			}

			torch.setLit(true);

			torch.setMsgable(mob_);

			WorldTime.addTickable(torch);

			mob_.out("You lite that ");
		} else {
			mob_.out("The " + item.getLook() + " is not litable.");
		}

	}

}
