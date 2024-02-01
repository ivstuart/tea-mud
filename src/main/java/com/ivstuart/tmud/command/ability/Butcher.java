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
import com.ivstuart.tmud.state.items.Corpse;
import com.ivstuart.tmud.state.items.Food;
import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.items.Prop;
import com.ivstuart.tmud.state.mobs.Mob;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Butcher extends BaseCommand {

    /*
     * Secondary action?
     */
    @Override
    public void execute(Mob mob, String input) {

        // Need any weapon with sword, knife, edge, sharp, axe, blade in the short desc
        // then also some animal meat - yields 3 portions of food that are perishable
        // salting meet makes it last 10 times longer.
        // Fire can cook meat but 30% change of burning it.

        Prop prop = mob.getRoom().getProps().get(input);

        Item item;
        boolean propFlag = false;
        if (!(prop instanceof Item)) {
            item = mob.getInventory().get(input);
            propFlag = true;
        } else {
            item = (Item) prop;
        }

        if (item == null) {
            mob.out("There is no " + input + " to butcher here");
            return;
        }

        if (!item.isButcherable()) {
            mob.out(input + " is not editable animal skin, can not butcher it");
            return;
        }

        if (!mob.getInventory().hasSharpEdge() && !mob.getEquipment().hasSharpEdge()) {
            mob.out(mob.getName() + " has no sharp edge capable of being used to butcher the animal");
            return;
        }

        // Butcher animal

        if (propFlag) {
            mob.getRoom().getProps().remove(item);
            if (item instanceof Corpse) {
                Corpse corpse = (Corpse) item;
                corpse.getInventory();
                mob.getRoom().getInventory().addAll(corpse.getInventory());
            }
        } else {
            mob.getInventory().remove(item);
        }

        Food animalMeat = new Food();

        animalMeat.setNumberPortions(item.getWeight());
        animalMeat.setWeight(item.getWeight());
        animalMeat.setBrief("some animal meat");
        animalMeat.setId("meat");
        animalMeat.setAlias("food meat");
        animalMeat.setSaltable(true);

        mob.out("You add some meat to your inventory");

        mob.getInventory().add(animalMeat);
    }

}
