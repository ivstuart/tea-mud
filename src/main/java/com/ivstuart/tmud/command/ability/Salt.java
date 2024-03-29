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

package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.items.Food;
import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.mobs.Mob;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Salt extends BaseCommand {

    /*
     * 2ndary action?
     */
    @Override
    public void execute(Mob mob, String input) {

        // Take salt from inventory and use it up.

        Item item = mob.getInventory().get(input);

        if (!(item instanceof Food)) {
            mob.out(input + " is not for salting");
            return;
        }

        Food food = (Food) item;

        if (!food.isSaltable()) {
            mob.out("Food is already full of salt");
            return;
        }

        mob.out("You add salt to that food");
        food.setSaltable(false);
        int portions = food.getPortions();
        food.setPortions(++portions);

        // Also have salt meat

    }

}
