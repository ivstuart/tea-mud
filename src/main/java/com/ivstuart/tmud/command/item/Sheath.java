/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Weapon;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sheath extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        // Check has a belt
        if (!mob.getEquipment().hasBelt()) {
            mob.out("You can not sheath a weapon without a belt!");
            return;
        }

        Item item = (Item) mob.getEquipment().getPrimary();

        if (item == null || (!(item instanceof Weapon))) {
            item = (Item) mob.getEquipment().getSecondary();
        }

        if (!(item instanceof Weapon)) {
            mob.out("You can only sheath a weapon :" + item);
            return;
        }

        if (!mob.getEquipment().hasThruBeltSlots()) {
            mob.out("You have already sheathed the maximum number of weapons through your belt");
            return;
        }

        mob.out("You sheath your " + item.getBrief() + " thru your belt");
        mob.getEquipment().sheath(item);

    }

}
