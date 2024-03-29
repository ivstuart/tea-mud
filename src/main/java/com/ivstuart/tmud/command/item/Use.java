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

package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.utils.MudArrayList;

/**
 * The following put modes are supported by the code below:
 * <p>
 * 1) put <object> <container> 2) put all.<object> <container> 3) put all
 * <container>
 *
 * <container> must be in inventory or on ground. all objects to be put into
 * container must be in inventory.
 */
public class Use extends BaseCommand {

    /**
     * @param input
     * @return
     */
    private boolean checkCashGet(Mob mob, String input) {

        int type = -1;
        if (input.indexOf("copper") > 0) {
            type = Money.COPPER;
        }
        if (input.indexOf("silver") > 0) {
            type = Money.SILVER;
        }
        if (input.indexOf("gold") > 0) {
            type = Money.GOLD;
        }
        if (type > -1) {
            String[] inputSplit = input.split(" ");
            if (inputSplit == null) {
                return false;
            }
            int coins;
            try {
                coins = Integer.parseInt(inputSplit[0]);
            } catch (NumberFormatException e) {
                return false;
            }

            Money cash = new Money(type, coins);

            if (mob.getRoom().remove(cash)) {
                mob.getInventory().add(cash);
            }
        }

        return false;
    }

    @Override
    public void execute(Mob mob, String input) {


        if (input.equalsIgnoreCase("all")) {
            getAllCoins(mob);
            return;
        }

        if (checkCashGet(mob, input)) {
            return;
        }

        MudArrayList<Item> items = mob.getRoom().getInventory().getItems();

        if (items == null) {
            mob.out(input + " is not here to get!");
            return;
        }
        Item anItem = items.remove(input);

        if (anItem == null) {
            mob.out("Can not get " + input + " it is not here!");
            return;
        }
        mob.getInventory().add(anItem);
        mob.out("You get an " + anItem);
    }

    private synchronized void getAllCoins(Mob mob) {

        SomeMoney money = mob.getRoom().getMoney();

        if (money != null) {
            mob.getInventory().add(money);
            money.clear();
        }
    }

}
