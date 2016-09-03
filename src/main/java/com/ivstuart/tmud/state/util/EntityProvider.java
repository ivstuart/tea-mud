package com.ivstuart.tmud.state.util;

import com.ivstuart.tmud.state.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        } else if (existingMob instanceof WarMaster) {
            LOGGER.debug("Creating instance of a Warmaster!");
            newMob = new WarMaster(existingMob);
        } else if (existingMob instanceof GuildMaster) {
            LOGGER.debug("Creating instance of a GuildMaster!");
            newMob = new GuildMaster(existingMob);
        } else if (existingMob instanceof ProfessionMaster) {
            LOGGER.debug("Creating instance of a ProfessionMaster!");
            newMob = new ProfessionMaster((ProfessionMaster) existingMob);
        }
        else {
            newMob = new Mob(existingMob);
        }

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
