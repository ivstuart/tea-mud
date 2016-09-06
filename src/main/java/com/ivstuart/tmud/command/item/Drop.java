/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.person.statistics.diseases.Disease;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.Torch;

import java.util.Iterator;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Drop extends BaseCommand {

	/*
	 * Usage: drop <item> drop all.<item> drop all drop <number> coins
	 */

	@Override
	public void execute(Mob mob, String input) {

		Room room = null;
		if (mob.getRoom().isFlying()) {
			room = mob.getRoom().getGroundRoom();
		} else {
			room = mob.getRoom();
		}

		if (input.equalsIgnoreCase("all")) {
			Iterator<Item> itemIter = mob.getInventory().getItems().iterator();
			for (; itemIter.hasNext(); ) {

				Item item = itemIter.next();

				room.add(item);

				mob.out("You drop an " + item.getBrief());

				checkDisease(mob, item);
			}
			return;
		}

		SomeMoney sm = mob.getInventory().removeCoins(input);

		if (sm != null) {
			mob.out("You drop some coins  " + sm);
			mob.getRoom().getInventory().add(sm);
			return;
		}

		Item item = mob.getInventory().remove(input);

		if (item == null) {
			mob.out("You are not carrying a " + input);
			return;
		}

		if (item instanceof Torch) {
			Torch torch = (Torch) item;
			torch.setMsgable(mob.getRoom());
		}

		room.add(item);

		mob.out("You drop an " + item.getBrief());

		checkDisease(mob, item);
	}

	private void checkDisease(Mob mob, Item item) {
		if (mob.getMobAffects().getDiseases() == null) {
			return;
		}

		for (Disease disease : mob.getMobAffects().getDiseases()) {
			if (disease.isIndirectContact()) {
				if (DiceRoll.ONE_D100.rollLessThanOrEqualTo(disease.getInfectionRate())) {
					Disease infection = (Disease) disease.clone();
					item.setDisease(infection);
				}
			}
		}

	}

}
