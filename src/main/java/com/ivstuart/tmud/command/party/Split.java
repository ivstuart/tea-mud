/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.party;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.command.item.Give;
import com.ivstuart.tmud.command.move.EnterNoLook;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.Mob;

import java.util.List;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Split implements Command {

	/**
	 * 
	 */
	public Split() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {

		String inputSplit[] = input.split(" ");

		int coins = 0;
		try {
			coins = Integer.parseInt(inputSplit[0]);
		} catch (NumberFormatException e) {
			mob.out("No number of coins specified");
		}

		Money money = new Money(Money.COPPER, coins);

		// Check has this much money available
		if (!mob.getInventory().hasMoney(money)) {
			mob.out("Nothing to split");
			return;
		}

		List<Mob> group = mob.getPlayer().getGroup();
		if (group == null) {
			mob.out("No group to split cash with");
			return;
		}

		int copperPerPerson = money.getQuantity() / group.size();

		// Give
		for (Mob aMob : group) {
			if (aMob != mob) {
				CommandProvider.getCommand(Give.class).execute(mob, copperPerPerson + " copper "+aMob.getName());
			}
		}

	}

}
