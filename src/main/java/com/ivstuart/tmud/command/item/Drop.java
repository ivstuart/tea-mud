/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Torch;

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

		Item item = mob.getInventory().remove(input);

		if (item == null) {
			mob.out("You are not carrying a " + input);
			return;
		}

		// TODO should this be factored into Room method called?
		if (item instanceof Torch) {
			Torch torch = (Torch) item;
			torch.setMsgable(mob.getRoom());
		}

		mob.getRoom().add(item);

		mob.out("You drop an " + item);
	}

}
