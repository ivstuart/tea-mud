package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Room;
import org.apache.log4j.Logger;

import java.util.List;

public class Janitor extends BaseBehaviour {

    private static Logger LOGGER = Logger.getLogger(Janitor.class);


    public Janitor() {
        parameter = 50;
    }

    @Override
    public String getId() {
        return mob.getId();
    }

    @Override
    public void tick() {

        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName()+" is does not feel like clearing up this tick");
            return;
        }

        Room room = mob.getRoom();

        if (room == null) {
            LOGGER.warn(mob.getName() + " has no room");
            return;
        }

        List<Item> items = room.getInventory().getItems();

        if (items == null || items.isEmpty()) {
            LOGGER.debug("Nothing to clean up");
            return;
        }

        Item item = null;

        try {
            item = items.remove(1);
        } catch (IndexOutOfBoundsException ioobe) {
            LOGGER.debug("Suddenly nothing to clean up");
        }

        if (item == null) {
            LOGGER.debug("Nothing to clean up.");
            return;
        }

        mob.getInventory().add(item);
    }

}
