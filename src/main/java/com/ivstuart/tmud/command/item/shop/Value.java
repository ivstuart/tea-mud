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
public class Value extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		Mob shopKeeper = mob.getRoom().getShopKeeper();

		if (shopKeeper == null) {
			mob.out("There is no shop here to buy and sell from");
			return;
		}

		Item item = mob.getInventory().get(input);

		if (item == null) {
			mob.out("You have no item "+input+" to value");
			return;
		}

		SomeMoney cost = item.getCost();

		if (cost == null) {
			cost = Money.NO_MONEY;
		}

		mob.out("Value of item is "+item.getCost());

	}

}
