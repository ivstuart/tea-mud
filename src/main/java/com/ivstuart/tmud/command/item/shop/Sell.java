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
package com.ivstuart.tmud.command.item.shop;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.ShopKeeper;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sell extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {
        ShopKeeper shopKeeper = mob.getRoom().getShopKeeper();

		if (shopKeeper == null) {
			mob.out("There is no shop here to buy and sell from");
			return;
		}

        if (mob.isGood() && shopKeeper.isNoGood() ||
                (!mob.isGood() && shopKeeper.isNoEvil())) {
            mob.out("This shop will not sell to the likes of you");
            return;
        }

        if (shopKeeper.isNoProfession(mob.getPlayer().getProfession())) {
            mob.out("This shop will not sell to your profession");
            return;
        }

		Item item = mob.getInventory().get(input);

		if (item == null) {
			mob.out("No item to sell "+input);
			return;
		}

		// SomeMoney
		Money cost = (Money) item.getCost();

		if (cost == null) {
			mob.out("The shop does not want that item its worthless");
			return;
		}

		if (!shopKeeper.getInventory().getPurse().remove(cost)) {
			mob.out("Shopkeeper can not afford "+cost+" of item");
			return;
		}

		mob.getInventory().remove(item);
		mob.getInventory().add(cost);

		shopKeeper.getInventory().add(item);

		mob.out("You sell a "+item.getName());
	}

}
