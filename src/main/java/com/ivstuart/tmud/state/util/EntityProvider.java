/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state.util;

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Prop;
import com.ivstuart.tmud.state.ShopKeeper;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.Iterator;

public class EntityProvider {

    private static final Logger LOGGER = LogManager.getLogger();

    public static Item createItem(String itemId) {
        LOGGER.debug("Creating instance of item " + itemId);
        return (Item) World.getItem(itemId).clone();
    }

    public static Mob createMob(String mobId_, String _id) {

        // LOGGER.info("Creating mob with id [ " + mobId_ + "]");

        Mob existingMob = World.getMobs().get(mobId_);

        if (existingMob == null) {
            LOGGER.error("No mob found for mob id:" + mobId_);
            return null;
        }

        Mob newMob = null;

        Class clazz = existingMob.getClass();
        Constructor constructor = null;
        Object mobObject = null;

        try {
            constructor = clazz.getConstructor(clazz);
            mobObject = constructor.newInstance(existingMob);
        } catch (Exception e) {
            LOGGER.error("Problem with new code ", e);
        }

        newMob = (Mob) mobObject;

        // this newMob is a new instance of Mob from world with its own repop
        // room
        newMob.setRepopRoomId(_id);


        if (!(newMob instanceof ShopKeeper)) {
            Item item = null;
            // Remove items based on load percentage default 1% load chance
            for (Iterator<Item> itemIter = newMob.getInventory().getItems().iterator(); itemIter.hasNext(); ) {
                item = itemIter.next();
                if (!item.isLoaded()) {
                    itemIter.remove();
                } else {
                    // Equip items.
                    newMob.getEquipment().add(item);
                }
            }
        }

        return newMob;
    }

    public static Prop createProp(String id_) {

        return World.getProp(id_);
    }
}
