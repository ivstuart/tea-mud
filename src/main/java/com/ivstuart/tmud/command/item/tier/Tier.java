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

package com.ivstuart.tmud.command.item.tier;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.util.EntityProvider;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
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
