/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Attribute;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Waterskin;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Drink extends BaseCommand {

	@Override
	public void execute(Mob mob_, String input_) {

		Item item = mob_.getInventory().get(input_);

		if (item == null) {
			item = (Item) mob_.getEquipment().get(input_);
		}

		/* Guard condition */
		if (item == null) {
			mob_.out("You are not carrying a " + input_ + " to drink.");
			return;
		}

		if (item instanceof Waterskin) {
			Waterskin waterskin = (Waterskin) item;

			if (waterskin.getDrafts() == 0) {
				mob_.out("You cannot drink from this as it is empty");
				return;
			}

			Attribute thirst = mob_.getPlayer().getData().getThirst();

			int max = thirst.getMaximum();

			int current = thirst.getValue();

			if (current + 20 > max) {
				mob_.out("You are not thirsty enough to drink");
				return;
			}

			mob_.out("You drink some liquard from " + waterskin);

			// TODO make decision about balancing it.

			thirst.increase(100);

			waterskin.drink();
		} else {
			mob_.out("The " + item.getLook() + " is not drinkable.");
		}

	}

}
