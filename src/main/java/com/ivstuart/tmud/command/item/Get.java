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
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.person.statistics.diseases.Disease;
import com.ivstuart.tmud.state.items.*;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.utils.MudArrayList;
import com.ivstuart.tmud.utils.StringUtil;

import java.util.Iterator;
import java.util.List;

import static com.ivstuart.tmud.utils.StringUtil.getFirstWord;
import static com.ivstuart.tmud.utils.StringUtil.getLastWord;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Get extends BaseCommand {

    /**
     * @param input
     * @return
     */
    private boolean checkCashGet(Mob mob, String input) {

        SomeMoney sm = mob.getRoom().getInventory().removeCoins(input);

        if (sm != null) {
            mob.getInventory().add(sm);
            mob.out("You get " + sm);
            return true;
        }

        return false;
    }

    /**
     * > get sword corpse
     * > get all corpse
     * > get all from corpse
     * > get all all.bag
     * > get all.bread
     * all.bag
     */
    @Override
    public void execute(Mob mob, String input) {


        if (input.equalsIgnoreCase("all")) {
            getAllCoins(mob);
        }

        if (checkCashGet(mob, input)) {
            return;
        }

        String lastWord = getLastWord(input);
        Prop prop = mob.getRoom().getProps().get(lastWord);

        if (prop != null) {
            if (prop instanceof Corpse) {
                Corpse corpse = (Corpse) prop;
                String target = StringUtil.getFirstFewWords(input);

                if (target.contains("all")) {
                    List<Item> items = corpse.getInventory().getItems();
                    mob.getInventory().addAll(items);
                    for (Item item : items) {
                        mob.out("You get " + item.getBrief() + " from " + corpse.getName());
                        checkItemForDisease(mob, item);
                    }
                    items.clear();
                }
                SomeMoney sm = corpse.getInventory().removeCoins(target);

                if (sm != null) {
                    mob.getInventory().add(sm);
                    mob.out("You get " + sm + " from " + corpse.getName());
                    return;
                }

                Item item = corpse.getInventory().get(target);

                if (item != null) {
                    corpse.getInventory().remove(item);
                    mob.getInventory().add(item);
                    mob.out("You get " + item.getBrief() + " from " + corpse.getName());
                    item.setHidden(false);
                    checkItemForDisease(mob, item);
                }

            }
        }

        MudArrayList<Item> items = mob.getRoom().getInventory().getItems();

        if (items == null) {
            mob.out(input + " is not here to get!");
            return;
        }

        if (input.equalsIgnoreCase("all")) {
            Iterator<Item> itemIter = items.iterator();
            while (itemIter.hasNext()) {
                Item item = itemIter.next();
                mob.getInventory().add(item);

                mob.out("You get an " + item.getBrief());
                itemIter.remove();
                item.setHidden(false);
                checkItemForDisease(mob, item);
            }
            return;
        }

        Item anItem = items.remove(input);

        // Check if getting an item out from a bag
        if (anItem == null) {

            String target = getLastWord(input);
            anItem = mob.getInventory().get(target);

            if (anItem == null) {
                anItem = mob.getRoom().getInventory().get(target);
            }

            if (anItem != null && anItem.isContainer()) {
                Bag bag = (Bag) anItem;

                if (bag instanceof Chest) {
                    if (((Chest) bag).getState() != DoorState.OPEN) {
                        mob.out("That item is not open");
                        return;
                    }
                }

                String itemString = getFirstWord(input);

                if (itemString.equalsIgnoreCase("all")) {
                    mob.getInventory().add(bag.getInventory().getPurse());
                    bag.getInventory().getPurse().clear();
                    Iterator<Item> itemIter = bag.getInventory().getItems().iterator();
                    while (itemIter.hasNext()) {
                        Item item = itemIter.next();
                        mob.getInventory().add(item);
                        mob.out("You get a " + item.getBrief() + " from " + bag.getName());
                        checkItemForDisease(mob, item);
                        itemIter.remove();
                    }
                    return;
                }


                Item bagItem = bag.getInventory().get(itemString);

                if (bagItem != null) {
                    mob.getInventory().add(bagItem);
                    bag.getInventory().remove(bagItem);
                    mob.out("You get a " + bagItem.getBrief() + " from " + bag.getName());
                    checkItemForDisease(mob, bagItem);
                    return;
                }

            }

        }

        if (anItem == null) {
            mob.out("Can not get " + input + " it is not here!");
            return;
        }

        mob.getInventory().add(anItem);

        mob.out("You get an " + anItem.getBrief());
        checkItemForDisease(mob, anItem);
    }

    private void checkItemForDisease(Mob mob, Item item) {
        if (item.getDisease() == null) {
            return;
        }
        Disease disease = item.getDisease();

        if (disease.isIndirectContact()) {
            Disease.infect(mob, disease);
        }
    }

    private void getAllCoins(Mob mob) {

        SomeMoney money = mob.getRoom().getMoney();

        if (money != null) {
            mob.getInventory().add(money);
            money.clear();
        }
    }

}
