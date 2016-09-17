/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
import com.ivstuart.tmud.world.World;

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
    private boolean checkDonateCash(Mob mob, String input) {
        SomeMoney sm = mob.getRoom().getInventory().removeCoins(input);

		if (sm != null) {
			mob.out("You donate " + sm);
			World.getDonateRoom(mob).getInventory().add(sm);
			return true;
		}

		return false;
	}

	@Override
	public void execute(Mob mob, String input) {
        Room donateRoom = World.getDonateRoom(mob);
        execute(mob, input, donateRoom);
    }

    public void execute(Mob mob, String input, Room donateRoom) {

        if (checkDonateCash(mob, input)) {
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

		if (anItem.isNoDonate()) {
			mob.out("Can not not donate this item its flagged as no donation");
			items.add(anItem);
			return;
		}

		World.getDonateRoom(mob).add(anItem);
		mob.out("You donate an " + anItem.getBrief());
	}

}
