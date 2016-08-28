/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.Equipable;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

import java.util.List;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Remove extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		if ("all".equalsIgnoreCase(input)) {
			removalAll(mob);
			return;
		}

		Item item = (Item) mob.getEquipment().remove(input);

		if (item == null) {
			mob.out("Can not get " + input + " it is not here!");
			return;
		}

		item.unequip(mob);

		mob.getInventory().add(item);

		mob.out("You remove an " + item);
	}

	private void removalAll(Mob mob) {
		List<Equipable> equipment = mob.getEquipment().removeAll();

		for (Equipable item : equipment) {
			mob.getInventory().add((Item) item);
		}

		for (Equipable eq : equipment) {
			mob.out("You remove an " + ((Item) eq).getName());
		}
	}

}
