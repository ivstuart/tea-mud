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
import com.ivstuart.tmud.person.statistics.diseases.Disease;
import com.ivstuart.tmud.state.Attribute;
import com.ivstuart.tmud.state.Food;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Eat extends BaseCommand {

    @Override
    public void execute(Mob mob_, String input_) {

        Item item = mob_.getInventory().get(input_);


        if (item == null) {
            mob_.out("You are not carrying a " + input_ + " to eat.");
            return;
        }

        if (item instanceof Food) {
            Food food = (Food) item;

            if (food.getPortions() == 0) {
                mob_.out("You cannot eat from this as it is empty");
                return;
            }

            Attribute hunger = mob_.getPlayer().getData().getHunger();

            int max = hunger.getMaximum();

            int current = hunger.getValue();

            if (current + 20 > max) {
                mob_.out("You are not hungry enough to eat");
                return;
            }

            hunger.increase(100);

            mob_.out("You eat some " + food);

            food.eat();

            checkFoodForDisease(mob_, food);

            if (food.getPortions() == 0) {
                mob_.getInventory().remove(food);
                mob_.out("Food is gone you ate it all of that!");
                // food object should be gc() able now.
            }
        } else {
            mob_.out("The " + item.getLook() + " is not drinkable.");
        }

    }

    private void checkFoodForDisease(Mob mob, Food food) {
        if (food.getDisease() == null) {
            return;
        }

        Disease disease = food.getDisease();

        if (disease.isOral()) {
            Disease.infect(mob, disease);
        }

    }


}
