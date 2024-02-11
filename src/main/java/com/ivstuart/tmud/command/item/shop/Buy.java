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
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.items.ItemEnum;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.mobs.ShopKeeper;
import com.ivstuart.tmud.state.util.EntityProvider;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
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
            mob.out("There is no item " + input + " to buy");
            return;
        }

        // SomeMoney
        SomeMoney cost = item.getCost();

        if (!mob.getInventory().getPurse().remove(cost)) {
            mob.out("You can not afford " + cost + " of item");
            return;
        }


        shopKeeper.getInventory().add(cost);

        // Infinite supply of items flag
        if (!item.hasItemEnum(ItemEnum.SHOP)) {
            shopKeeper.getInventory().remove(item);
        } else {
            item = EntityProvider.createItem(item.getId());
        }

        item.removeItemEnum(ItemEnum.SHOP);

        mob.getInventory().add(item);

        mob.out("You buy a " + item.getName());

    }

}
