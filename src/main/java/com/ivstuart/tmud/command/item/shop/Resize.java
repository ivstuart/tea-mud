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
public class Resize extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		// resize item
		// resize all

		Mob repairer = mob.getRoom().getRepairer();

		if (repairer == null) {
			mob.out("No one here willing to resize your kit");
			return;
		}

		if (input.equalsIgnoreCase("all")) {
			for (Item item : mob.getInventory().getItems()) {
				resizeItem(mob, input, item);
			}
			return;
		}

		Item item = mob.getInventory().get(input);

		resizeItem(mob, input, item);

	}

	private void resizeItem(Mob mob, String input, Item item) {
		if (item == null) {
			mob.out("No such item " + input);
			return;
		}
		int mobSize = 0;

		try {
			mobSize = Integer.parseInt(mob.getSize());
		}catch (NumberFormatException nfe) {
			mob.out("Mob size is "+mob.getSize()+" which is a number format exception");
			mobSize = 3;
		}

		if (mobSize == item.getSize()) {
			mob.out("Item is already of the correct size to use");
			return;
		}

		// 10% rounded up to resize
		int cost = 1 + (item.getCost().getValue() * 10) / 100;

		mob.out("The item will cost " + cost + " copper to resize it");

		if (!mob.getInventory().getPurse().remove(new Money(Money.COPPER, cost))) {
			mob.out("You do not have the required funds to make this resize");
			return;
		}

		item.setSize(mobSize);

		mob.out("Item " + item.getName() + " is resized");
	}
}
