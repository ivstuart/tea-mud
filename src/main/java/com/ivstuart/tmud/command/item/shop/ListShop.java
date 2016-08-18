/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item.shop;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ListShop extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {
		Mob shopKeeper = mob.getRoom().getShopKeeper();

		if (shopKeeper == null) {
			mob.out("There is no shop here to buy and sell from");
			return;
		}

		// list

		mob.out("$H~$J");

		int index = 1;
		for (Item item : shopKeeper.getInventory().getItems()) {
			mob.out("["+index+"] "+item.getName()+ " at "+item.getCost());
			index++;
		}

		mob.out("$H~$J");

	}

}
