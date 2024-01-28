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
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Resize extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        // resize item
        // resize all

        Mob repairer = mob.getRoom().getRepairer();

        if (repairer == null) {
            mob.out("No one here willing to resize your kit");
            return;
        }

        if (input.equalsIgnoreCase("all")) {
            for (Item item : mob.getInventory().getItems()) {
                resizeItem(mob, input, item);
            }
            return;
        }

        Item item = mob.getInventory().get(input);

        resizeItem(mob, input, item);

    }

    private void resizeItem(Mob mob, String input, Item item) {
        if (item == null) {
            mob.out("No such item " + input);
            return;
        }
        int mobSize = mob.getMobBodyStats().getHeight();

        if (mobSize == 0) {
            mobSize = 180;
        }


        if (item.isCorrectSize(mobSize)) {
            mob.out("Item is already of the correct size to use");
            return;
        }

        // 10% rounded up to resize
        int cost = 1 + (item.getCost().getValue() * 10) / 100;

        mob.out("The item will cost " + cost + " copper to resize it");

        if (!mob.getInventory().getPurse().remove(new Money(Money.COPPER, cost))) {
            mob.out("You do not have the required funds to make this resize");
            return;
        }

        item.setSize(mobSize);

        mob.out("Item " + item.getName() + " is resized");
    }
}
