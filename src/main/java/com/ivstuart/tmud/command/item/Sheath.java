/*
 *  Copyright 2016. Ivan Stuart
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
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
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

        if (!(item instanceof Weapon)) {
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
