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
import com.ivstuart.tmud.common.Equipable;
import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.items.ItemEnum;
import com.ivstuart.tmud.state.mobs.Mob;

import java.util.List;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Remove extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        if ("all".equalsIgnoreCase(input)) {
            removalAll(mob);
            return;
        }

        Item item = (Item) mob.getEquipment().remove(input);

        if (item == null) {
            mob.out("Can not get " + input + " it is not here!");
            return;
        }

        if (item.hasItemEnum(ItemEnum.NO_REMOVE)) {
            mob.getEquipment().add(item);
            mob.out("This item is cursed and will not be removed");
            return;
        }

        item.unequip(mob);

        mob.getInventory().add(item);

        mob.out("You remove an " + item.getBrief());
    }

    private void removalAll(Mob mob) {
        List<Equipable> equipment = mob.getEquipment().removeAll();

        for (Equipable item : equipment) {
            mob.getInventory().add((Item) item);
        }

        for (Equipable eq : equipment) {
            mob.out("You remove an " + ((Item) eq).getName());
        }
    }

}
