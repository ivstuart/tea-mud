package com.ivstuart.tmud.state.util;

import com.ivstuart.tmud.state.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        // TODO rethink this design as it does not scale for many mob types.
        if (existingMob.isGuard()) {
            newMob = new GuardMob(existingMob);
        } else if (existingMob instanceof ShopKeeper) {
            LOGGER.debug("Creating instance of a shop keeper!");
            newMob = new ShopKeeper(existingMob);
        } else if (existingMob instanceof Banker) {
            LOGGER.debug("Creating instance of a Banker!");
            newMob = new Banker(existingMob);
        } else if (existingMob instanceof Armourer) {
            LOGGER.debug("Creating instance of a Armourer!");
            newMob = new Armourer(existingMob);
        } else {
            newMob = new Mob(existingMob);
        }

        // this newMob is a new instance of Mob from world with its own repop
        // room
        newMob.setRepopRoomId(_id);

        return newMob;
    }

    public static Prop createProp(String id_) {

        return World.getProp(id_);
    }
}
