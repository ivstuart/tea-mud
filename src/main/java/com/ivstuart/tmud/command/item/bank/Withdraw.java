/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
public class Withdraw extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		Mob banker = mob.getRoom().getBanker();

		if (banker == null) {
			mob.out("There is no bank here to withdraw or deposit from");
			return;
		}

		if (checkCashWithdraw(mob,banker,input)) {
			return;
		}
		// items and cash amounts.

		Item item = mob.getPlayer().getBank().get(input);

		if (item == null) {
			mob.out("There is no item "+input+" to withdraw");
			return;
		}

		SomeMoney cost = item.getCost();

		if (cost == null) {
			cost = Money.NO_MONEY;
		}

		cost = new Money(Money.COPPER, (int) (1 + cost.getValue() * 0.05));

		if (!mob.getInventory().getPurse().remove(cost)) {
			mob.out("You can not afford "+cost+" to withdraw item");
			return;
		}

		mob.getPlayer().getBank().remove(item);
		banker.getInventory().add(cost);

		mob.getInventory().add(item);

		mob.out("You withdraw a "+item.getName());

	}

	private boolean checkCashWithdraw(Mob mob, Mob banker, String input) {

		SomeMoney cash = mob.getPlayer().getBank().removeCoins(input);

		if (cash == null) {
			return false;
		}

		mob.getInventory().add(cash);

		mob.out("You withdraw "+cash+" from the bank");

		return true;

	}
}
