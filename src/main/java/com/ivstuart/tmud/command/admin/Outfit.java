/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.util.EntityProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Ivan on 08/09/2016.
 */
public class Outfit extends AdminCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(Mob mob, String input) {

        Item item = EntityProvider.createItem("immortal-80");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("black-boots-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("black-anklet-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("black-anklet-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("wizard-hat-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("ring-fire-prot-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("ring-of-combat-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("mantel-of-protection-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("orb-of-power-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("jade-earring-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("jade-earring-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("cool-shades-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("mask-madness-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("spiked-collar-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("spiked-pauldrons-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("grey-cloak-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("chest-plate-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("greaves-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("gauntlets-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("bangle-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("leather-belt-001");
        mob.getInventory().add(item);

        item = EntityProvider.createItem("chain-mail-leggings-001");
        mob.getInventory().add(item);

        mob.getInventory().add(new Money(Money.PLATINUM, 10000));
    }
}
