/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item.shop;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.ShopKeeper;
import com.ivstuart.tmud.state.util.EntityProvider;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Buy extends BaseCommand {

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

		// buy 1
		// buy 1.sword
		// buy sword
		// buy all - surely not

		Item item = shopKeeper.getInventory().get(input);

		if (item == null) {
			mob.out("There is no item "+input+" to buy");
			return;
		}

		// SomeMoney
		SomeMoney cost = item.getCost();

		if (!mob.getInventory().getPurse().remove(cost)) {
			mob.out("You can not afford "+cost+" of item");
			return;
		}


		shopKeeper.getInventory().add(cost);

		// Infinite supply of items flag
		if (!item.isShopSupplied()) {
			shopKeeper.getInventory().remove(item);
		} else {
			item = EntityProvider.createItem(item.getId());
		}

        item.setShopSupplied(false);

		mob.getInventory().add(item);

		mob.out("You buy a "+item.getName());

	}

}
