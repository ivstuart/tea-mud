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

        createItem(mob, "immortal-80");
        createItem(mob, "black-boots-001");
        createItem(mob, "black-anklet-001");
        createItem(mob, "black-anklet-001");
        createItem(mob, "wizard-hat-001");
        createItem(mob, "ring-fire-prot-001");
        createItem(mob, "ring-of-combat-001");
        createItem(mob, "mantel-of-protection-001");
        createItem(mob, "orb-of-power-001");
        createItem(mob, "jade-earring-001");
        createItem(mob, "jade-earring-001");
        createItem(mob, "cool-shades-001");
        createItem(mob, "mask-madness-001");
        createItem(mob, "spiked-collar-001");
        createItem(mob, "spiked-pauldrons-001");
        createItem(mob, "grey-cloak-001");
        createItem(mob, "chest-plate-001");
        createItem(mob, "greaves-001");
        createItem(mob, "gauntlets-001");
        createItem(mob, "bangle-001");
        createItem(mob, "bangle-001");
        createItem(mob, "leather-belt-001");
        createItem(mob, "chain-mail-leggings-001");

        mob.getInventory().add(new Money(Money.PLATINUM, 10000));
    }

    private void createItem(Mob mob, String id) {
        Item item = EntityProvider.createItem(id);
        mob.getInventory().add(item);
    }
}
