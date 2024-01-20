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
import com.ivstuart.tmud.state.*;
import com.ivstuart.tmud.utils.StringUtil;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Drink extends BaseCommand {

    @Override
    public void execute(Mob mob_, String input_) {

        String target = StringUtil.getLastWord(input_);
        // Check for fountain first of all
        Prop fountain = mob_.getRoom().getProps().get(target);

        if (fountain != null) {
            if (fountain.isWaterSource()) {
                mob_.out("You drink some liquid from " + fountain.getName());
                Attribute thirst = mob_.getPlayer().getData().getThirst();
                thirst.increase(500);
                return;
            }
        }

        Item item = mob_.getInventory().get(input_);

        if (item == null) {
            item = (Item) mob_.getEquipment().get(input_);
        }

        /* Guard condition */
        if (item == null) {
            mob_.out("You are not carrying a " + input_ + " to drink.");
            return;
        }

        if (item instanceof Potion) {
            Potion potion = (Potion) item;

            mob_.out("You quaff the " + item.getLook() + " down");

            potion.drink(mob_);

            mob_.getInventory().remove(potion);
            return;
        }

        if (item instanceof Waterskin) {
            Waterskin waterskin = (Waterskin) item;

            if (waterskin.getDrafts() == 0) {
                mob_.out("You cannot drink from this as it is empty");
                return;
            }

            Attribute thirst = mob_.getPlayer().getData().getThirst();

            int max = thirst.getMaximum();

            int current = thirst.getValue();

            if (current + 20 > max) {
                mob_.out("You are not thirsty enough to drink");
                return;
            }

            mob_.out("You drink some liquid from " + waterskin.getBrief());

            thirst.increase(waterskin.getLiquidType().getThirst());

            mob_.getPlayer().getData().getHunger().increase(waterskin.getLiquidType().getFood());
            mob_.getPlayer().getData().getDrunkAttribute().increase(waterskin.getLiquidType().getAlcohol());
            mob_.getPlayer().getData().getPoisonAttribute().increase(waterskin.getLiquidType().getPoison());

            waterskin.drink();
        } else {
            mob_.out("The " + item.getLook() + " is not drinkable.");
        }

    }

}
