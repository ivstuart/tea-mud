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
import com.ivstuart.tmud.constants.DoorState;
import com.ivstuart.tmud.state.items.Bag;
import com.ivstuart.tmud.state.items.Chest;
import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.mobs.Mob;

import static com.ivstuart.tmud.utils.StringUtil.getFirstWord;
import static com.ivstuart.tmud.utils.StringUtil.getLastWord;

/**
 * The following put modes are supported by the code below:
 * <p>
 * 1) put <object> <container> 2) put all.<object> <container> 3) put all
 * <container>
 *
 * <container> must be in inventory or on ground. all objects to be put into
 * container must be in inventory.
 */
public class Put extends BaseCommand {


    @Override
    public void execute(Mob mob, String input) {

        String bagString = getLastWord(input);

        Item bag = mob.getInventory().get(bagString);

        if (bag == null) {
            bag = mob.getRoom().getInventory().get(bagString);
        }

        if (bag == null) {
            mob.out("There is no bag to put stuff into");
            return;
        }

        if (!bag.isContainer()) {
            mob.out("You can not put things into " + bag.getName());
            return;
        }


        String target = getFirstWord(input);

        Item anItem = mob.getInventory().get(target);

        if (anItem == null) {
            mob.out("Can not put " + target + " it is not here!");
            return;
        }

        if (anItem == bag) {
            mob.out("You can not put something into itself");
            return;
        }

        Bag aBag = (Bag) bag;

        if (bag instanceof Chest) {
            if (((Chest) bag).getState() != DoorState.OPEN) {
                mob.out("That item is not open");
                return;
            }
        }

        aBag.getInventory().add(anItem);
        mob.getInventory().remove(anItem);

        mob.out("You put an " + anItem.getName() + " into a " + bag.getName());


    }


}
