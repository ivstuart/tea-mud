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
import com.ivstuart.tmud.state.Torch;
import com.ivstuart.tmud.state.WorldTime;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Unlite implements Command {

	@Override
	public void execute(Mob mob_, String input_) {

		Item item = mob_.getInventory().get(input_);

		if (item == null) {
			item = (Item) mob_.getEquipment().get(input_);
		}

		if (item == null) {
			mob_.out("You are not carrying a " + input_ + " to unlite.");
			return;
		}

		if (item instanceof Torch) {
			Torch torch = (Torch) item;

			if (torch.isLit() == false) {
				mob_.out("That is not alight!");
				return;
			}

			torch.setLit(false);

			torch.setMsgable(null);

			WorldTime.removeItem(torch);

			mob_.out("You unlight that ");
		} else {
			mob_.out("The " + item.getLook() + " is not unlitable.");
		}

	}

}
