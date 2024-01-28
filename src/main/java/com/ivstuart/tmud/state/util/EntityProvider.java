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

        Class<?> clazz = existingMob.getClass();
        Constructor constructor;
        Object mobObject = null;

        try {
            constructor = clazz.getConstructor(clazz);
            mobObject = constructor.newInstance(existingMob);
        } catch (Exception e) {
            LOGGER.error("Problem with new code ", e);
        }

        if (mobObject instanceof Mob) {
            newMob = (Mob) mobObject;
        }

        if (newMob == null) {
            LOGGER.error("Mob object " + mobObject + " not an instance of a Mob");
            return null;
        }

        // this newMob is a new instance of Mob from world with its own repop
        // room
        newMob.setRepopRoomId(_id);


        if (!(newMob instanceof ShopKeeper)) {
            Item item;
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
