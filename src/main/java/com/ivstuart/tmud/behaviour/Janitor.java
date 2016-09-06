/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Janitor extends BaseBehaviour {

    private static Logger LOGGER = LogManager.getLogger();


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
            LOGGER.debug(mob.getName()+" is does not feel like clearing up this tick");
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
            item = items.remove(1);
        } catch (IndexOutOfBoundsException ioobe) {
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
