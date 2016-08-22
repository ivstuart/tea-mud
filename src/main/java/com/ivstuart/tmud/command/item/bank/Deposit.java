/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item.bank;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Deposit extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		Mob banker = mob.getRoom().getBanker();

		if (banker == null) {
			mob.out("There is no bank here to deposit into");
			return;
		}

		if (checkCashDeposit(mob, banker, input)) {
			return;
		}

		Item item = mob.getInventory().get(input);

		if (item == null) {
			mob.out("No item to deposit "+input);
			return;
		}

		mob.getInventory().remove(item);

		mob.getPlayer().getBank().add(item);

		mob.out("You deposit a "+item.getName());
	}

	private boolean checkCashDeposit(Mob mob, Mob banker, String input) {

		SomeMoney cash = mob.getInventory().removeCoins(input);

		if (cash == null) {
			return false;
		}

		banker.getInventory().add(cash);

		mob.out("You deposit "+cash+" into the bank");

		return true;
	}

}
