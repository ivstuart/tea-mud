/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item.shop;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sell extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {
		Mob shopKeeper = mob.getRoom().getShopKeeper();

		if (shopKeeper == null) {
			mob.out("There is no shop here to buy and sell from");
			return;
		}


		Item item = mob.getInventory().get(input);

		if (item == null) {
			mob.out("No item to sell "+input);
			return;
		}

		// SomeMoney
		Money cost = (Money) item.getCost();

		if (cost == null) {
			mob.out("The shop does not want that item its worthless");
			return;
		}

		if (!shopKeeper.getInventory().getPurse().remove(cost)) {
			mob.out("Shopkeeper can not afford "+cost+" of item");
			return;
		}

		mob.getInventory().remove(item);
		mob.getInventory().add(cost);

		shopKeeper.getInventory().add(item);

		mob.out("You sell a "+item.getName());
	}

}
