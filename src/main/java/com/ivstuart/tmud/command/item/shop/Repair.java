/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item.shop;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Repair extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		// repair item
		// repair all

		Mob repairer = mob.getRoom().getRepairer();

		if (repairer == null) {
			mob.out("No one here willing to repair your kit");
			return;
		}

		if (input.equalsIgnoreCase("all")) {
			for (Item item : mob.getInventory().getItems()) {
				repairItem(mob, input, item);
			}
			return;
		}

		Item item = mob.getInventory().get(input);

		repairItem(mob, input, item);

	}

	private void repairItem(Mob mob, String input, Item item) {
		if (item == null) {
			mob.out("No such item "+input);
			return;
		}

		if (item.getDamagedPercentage() < 1) {
			mob.out("Item has no damage to repair");
			return;
		}

		int cost = (item.getCost().getValue() * item.getDamagedPercentage()) / 100;

		mob.out("The item will cost "+cost+" copper to repair it");

		if (!mob.getInventory().getPurse().remove(new Money(Money.COPPER,cost))) {
			mob.out("You do not have the required funds to make this repair");
			return;
		}

		item.setDamagedPercentage(0);

		mob.out("Item "+item.getName()+" is fully repaired");
	}

}
