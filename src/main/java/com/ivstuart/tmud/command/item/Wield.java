/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.common.Equipable;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Wield implements Command {

	@Override
	public void execute(Mob mob, String input) {

		Item item = mob.getInventory().get(input); // .remove(input);

		if (item == null) {
			mob.out("You are not carrying a " + input);
			return;
		}

		Equipable eq = null;

		if (item instanceof Equipable) {
			eq = item;
		} else {
			mob.out("That item is not equipable!");
			return;
		}

		if (mob.getEquipment().add(eq)) {
			mob.getInventory().remove(item);
			eq.equip(mob);
			mob.out("You wear an " + item);
		} else {
			mob.out("You do not have any space available to wear an " + item);
			// mob.getInventory().add(item);
		}

	}

}
