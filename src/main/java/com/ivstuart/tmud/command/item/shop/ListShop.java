/*
 *  Copyright 2024. Ivan Stuart
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

package com.ivstuart.tmud.command.item.shop;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.ShopKeeper;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ListShop extends BaseCommand {

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


        // list

        mob.out("$H~$J");

        // Required to keep the order the same.
        for (int index = 0; index < shopKeeper.getInventory().getItems().size(); index++) {
            Item item = shopKeeper.getInventory().getItems().get(index);
            mob.out("[" + (index + 1) + "] " + item.getName() + " at " + item.getCost());
        }

        mob.out("$H~$J");

    }

}
