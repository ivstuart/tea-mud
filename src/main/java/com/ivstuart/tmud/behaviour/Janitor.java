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

package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.places.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Janitor extends BaseBehaviour {

    private final static Logger LOGGER = LogManager.getLogger();


    public Janitor() {
        parameter = 50;
    }

    @Override
    public String getId() {
        return mob.getId();
    }

    @Override
    public boolean tick() {

        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName() + " is does not feel like clearing up this tick");
            return false;
        }

        Room room = mob.getRoom();

        if (room == null) {
            LOGGER.warn(mob.getName() + " has no room");
            return false;
        }

        List<Item> items = room.getInventory().getItems();

        if (items == null || items.isEmpty()) {
            LOGGER.debug("Nothing to clean up");
            return false;
        }

        Item item = null;

        try {
            item = items.remove(0);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.debug("Suddenly nothing to clean up");
        }

        if (item == null) {
            LOGGER.debug("Nothing to clean up.");
            return false;
        }

        mob.getInventory().add(item);

        return false;
    }

}
