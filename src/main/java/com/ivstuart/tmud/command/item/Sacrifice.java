/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.utils.*;

/**
 * @author stuarti TODO check for containers to ensure they are empty 1st check
 *         flag for none sac items... maybe gems should be none sacable and
 *         other powerful items.
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sacrifice implements Command {

	/**
	 * @param input
	 * @return
	 */
	private boolean checkCashGet(Mob mob, String input) {
		// TODO Auto-generated method stub
		int type = -1;
		if (input.indexOf("copper") > 0) {
			type = Money.COPPER;
		}
		if (input.indexOf("silver") > 0) {
			type = Money.SILVER;
		}
		if (input.indexOf("gold") > 0) {
			type = Money.GOLD;
		}
		if (type > -1) {
			String inputSplit[] = input.split(" ");
			if (inputSplit == null) {
				return false;
			}
			int coins = 0;
			try {
				coins = Integer.parseInt(inputSplit[0]);
			} catch (NumberFormatException e) {
				return false;
			}

			Money cash = new Money(type, coins);

			if (mob.getRoom().remove(cash)) {
				mob.getInventory().add(cash);
			}
		}

		return false;
	}

	@Override
	public void execute(Mob mob, String input) {

		// TODO Auto-generated method stub

		if (input.equalsIgnoreCase("all")) {
			getAllCoins(mob);
			return;
		}

		if (checkCashGet(mob, input)) {
			return;
		}

		MudArrayList<Item> items = mob.getRoom().getItems();
		if (items == null) {
			mob.out(input + " is not here to sacrifice!");
			return;
		}
		Item anItem = items.remove(input);

		if (anItem == null) {
			mob.out("Can not sacrifice " + input + " it is not here!");
			return;
		}
		// mob.getInventory().add(anItem);
		mob.out("You sacrifice an " + anItem);
	}

	// TODO threadsafety
	private void getAllCoins(Mob mob) {

		SomeMoney money = mob.getRoom().getMoney();

		if (money != null) {
			// mob.getInventory().add(money);
			money.clear();
		}
	}

}
