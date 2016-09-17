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

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Wield extends BaseCommand {

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

		if (item.isAntiProfession(mob.getPlayer().getProfession())) {
			mob.out("You can not wield that item its not for your profession");
			return;
		}

        if (!item.isCorrectSize(mob.getHeight())) {
            mob.out("That item is the wrong size to wield, resize it first");
            return;
        }

		if (mob.getEquipment().add(eq)) {
			mob.getInventory().remove(item);
			eq.equip(mob);
			mob.out("You wield an " + item);
		} else {
			mob.out("You do not have any space available to wield an " + item);
		}

	}

}
