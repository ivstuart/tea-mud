/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.common.Equipable;
import com.ivstuart.tmud.server.Login;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import org.apache.log4j.Logger;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Wear extends BaseCommand {

	private static final Logger LOGGER = Logger.getLogger(Wear.class);

	@Override
	public void execute(Mob mob, String input) {

		// LOGGER.debug("Mob "+mob.getName()+" attempts to wear "+input);

		if ("all".equalsIgnoreCase(input)) {
			Iterator<Item> itemIter = mob.getInventory().iterator();

			if (!itemIter.hasNext()) {
				mob.out("You have nothing to wear or equip");
				return;
			}

			for (Item item = itemIter.next();itemIter.hasNext();item = itemIter.next()) {
				if (mob.getEquipment().add(item)) {
					itemIter.remove();
					item.equip(mob);
					mob.out("You wear an " + item.getName());
				} else {
					mob.out("You do not have any space available to wear an " + item.getName());
					// mob.getInventory().add(item);
				}
			}
			return;
		}


		Item item = mob.getInventory().get(input);

		if (item == null) {
			mob.out("You are not carrying a " + input);
			return;
		}

		wearItem(mob, item);

	}

	private void wearItem(Mob mob, Item item) {
		if (!(item instanceof Equipable)) {
			mob.out("That item is not equipable!");
			return;
		}

		if (mob.getEquipment().add(item)) {
			mob.getInventory().remove(item);
			item.equip(mob);
			mob.out("You wear an " + item.getName());
		} else {
			mob.out("You do not have any space available to wear an " + item.getName());
			// mob.getInventory().add(item);
		}
	}

}
