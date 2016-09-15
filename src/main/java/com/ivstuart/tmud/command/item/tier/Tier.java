/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item.tier;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.util.EntityProvider;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Tier extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        Mob warMaster = mob.getRoom().getWarMaster();

        if (warMaster == null) {
            mob.out("There is no war master here to tier from");
            return;
        }
        int tier = mob.getPlayer().getData().getTier();
        int wp = mob.getPlayer().getData().getWarpoints();

        if (hasReachNewTier(tier, wp)) {
            tier++;
            mob.getPlayer().getData().setTier(tier);
            mob.out("You have reached the next tier well done");

            giveTierEquipmentToMob(mob, tier);

        } else {
            mob.out("You need more points to reach the next tier.");
        }

    }

    private void giveTierEquipmentToMob(Mob mob, int tier) {
        String armour = "tier-00" + tier;
        Item item = EntityProvider.createItem(armour);
        mob.getInventory().add(item);
        mob.out("You are given by the war master " + item.getBrief() + " into your backpack");

    }

    private boolean hasReachNewTier(int tier, int wp) {

        switch (tier) {
            case 0:
                return wp >= 10;
            case 1:
                return wp >= 50;
            case 2:
                return wp >= 200;
            case 3:
                return wp >= 500;
            case 4:
                return wp >= 1000;
            default:
                return false;
        }

    }

}
