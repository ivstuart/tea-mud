/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item.bank;

import com.ivstuart.tmud.command.BaseCommand;
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
public class ListBank extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		Mob banker = mob.getRoom().getBanker();

		if (banker == null) {
			mob.out("There is no bank here to list items");
			return;
		}

		// list

		mob.out("$H~$J");

		int index = 1;
		for (Item item : mob.getPlayer().getBank().getItems()) {
			SomeMoney cost = item.getCost();
			if (cost == null) {
				cost = Money.NO_MONEY;
			}
			cost = new Money(Money.COPPER, (int) (1 + cost.getValue() * 0.05));
			mob.out("["+index+"] "+item.getName()+ " at "+cost);
			index++;
		}


        // mob.out("Funds: "+banker.getInventory().getPurseString());
        mob.out("Funds: " + mob.getPlayer().getBank().getPurseString());
        mob.out("$H~$J");

	}

}
