/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.Equipable;
import com.ivstuart.tmud.constants.EquipLocation;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Wear extends BaseCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(Mob mob, String input) {

        // LOGGER.debug("Mob "+mob.getName()+" attempts to wear "+input);

        if ("all".equalsIgnoreCase(input)) {
            Iterator<Item> itemIter = mob.getInventory().iterator();

            if (!itemIter.hasNext()) {
                mob.out("You have nothing to wear or equip");
                return;
            }

            for (; itemIter.hasNext(); ) {
                Item item = itemIter.next();
                // wearItem(mob,item);
                if (item.isAntiProfession(mob.getPlayer().getProfession())) {
                    mob.out("You can not wear that item its not for your profession");
                    continue;
                }

                if (!item.isCorrectSize(mob.getHeight())) {
                    mob.out("That item is the wrong size to wear, resize it first");
                    continue;
                }

                if (mob.getEquipment().add(item)) {
                    itemIter.remove();
                    item.equip(mob);
                    mob.out("You wear an " + item.getName());
                } else {
                    mob.out("You do not have any space available to wear an " + item.getName());

                }
            }
            return;
        }

        Item item = mob.getInventory().get(input);

        if (item == null) {
            mob.out("You are not carrying a " + input);
            return;
        }

        if (item.isAntiProfession(mob.getPlayer().getProfession())) {
            mob.out("You can not wear that item its not for your profession");
            return;
        }

        wearItem(mob, item);

    }

    private void wearItem(Mob mob, Item item) {
        if (!(item instanceof Equipable)) {
            mob.out("That item is not equipable!");
            return;
        }

        if (item.isAntiProfession(mob.getPlayer().getProfession())) {
            mob.out("You can not wear that item its not for your profession");
            return;
        }

        if (item.isAntiGood() && mob.isGood()) {
            mob.out("You can not wear this item it is anti good");
            return;
        }

        if (item.isAntiEvil() && !mob.isGood()) {
            mob.out("You can not wear this item it is anti evil");
            return;
        }


        if (item.getDamagedPercentage() > 99) {
            mob.out("That item is too damaged to use, repair it");
            return;
        }

        if (!item.isCorrectSize(mob.getHeight())) {
            mob.out("That item is the wrong size to wear, resize it first");
            return;
        }

        if (mob.getEquipment().add(item)) {
            mob.getInventory().remove(item);
            item.equip(mob);

            if (item.getWorn() == EquipLocation.PRIMARY.ordinal() ||
                    item.getWorn() == EquipLocation.BOTH.ordinal() ||
                    item.getWorn() == EquipLocation.SECONDARY.ordinal()) {
                mob.out("You hold an " + item.getBrief());
            } else {
                mob.out("You wear an " + item.getBrief());
            }
        } else {
            mob.out("You do not have any space available to wear an " + item.getBrief());
            // mob.getInventory().add(item);
        }
    }

}
