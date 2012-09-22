/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Waterskin;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Pour implements Command {
	/**
	 * Usage: pour <from container> <to container> pour <from container> out
	 * 
	 * If you want to pour some liquid from one container to another. Pouring
	 * out simply empties the contents of the container onto the ground.
	 */
	@Override
	public void execute(Mob mob_, String input_) {

		Item item = mob_.getInventory().get(input_);

		if (item == null) {
			item = (Item) mob_.getEquipment().get(input_);
		}

		if (item == null) {
			mob_.out("You are not carrying a " + input_ + " to empty.");
			return;
		}

		if (item instanceof Waterskin) {
			Waterskin waterskin = (Waterskin) item;

			if (waterskin.getDrafts() == 0) {
				mob_.out("You empty this but it was already empty");
				return;
			}

			mob_.out("You empty some liquard from " + waterskin);

			waterskin.empty();
		} else {
			mob_.out("The " + item.getLook() + " is not emptyable.");
		}

	}

}
