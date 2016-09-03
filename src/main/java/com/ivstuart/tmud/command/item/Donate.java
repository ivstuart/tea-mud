/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.*;
import com.ivstuart.tmud.utils.MudArrayList;
import com.ivstuart.tmud.utils.StringUtil;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Donate extends BaseCommand {

	// Teleports item to donation room
	// Idea is newbies can pick up items.

	/**
	 * @param input
	 * @return
	 */
	private boolean checkSacCash(Mob mob, String input) {
		SomeMoney sm = mob.getRoom().getInventory().removeCoins(input);

		if (sm != null) {
			mob.out("You sacrifice " + sm);
			World.getDonateRoom(mob).getInventory().add(sm);
			return true;
		}

		return false;
	}

	@Override
	public void execute(Mob mob, String input) {

		if (checkSacCash(mob, input)) {
			return;
		}

		String lastWord = StringUtil.getLastWord(input);
		Prop prop = mob.getRoom().getProps().get(lastWord);

		if (prop != null) {
			if (prop instanceof Corpse) {
				Corpse corpse = (Corpse) prop;
				mob.getRoom().remove(corpse);
				mob.out("You donate an " + corpse.getName());
				return;
			}
		}

		MudArrayList<Item> items = mob.getRoom().getInventory().getItems();

		if (items == null) {
			mob.out(input + " is not here to donate!");
			return;
		}

		Item anItem = items.remove(input);

		if (anItem == null) {
			mob.out("Can not donate " + input + " it is not here!");
			return;
		}

		World.getDonateRoom(mob).add(anItem);
		mob.out("You donate an " + anItem);
	}

}
