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
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.person.statistics.diseases.Disease;
import com.ivstuart.tmud.state.*;

import java.util.Iterator;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Drop extends BaseCommand {

    /*
     * Usage: drop <item> drop all.<item> drop all drop <number> coins
     */

    @Override
    public void execute(Mob mob, String input) {

        Room room = null;
        if (mob.getRoom().hasFlag(RoomEnum.AIR)) {
            room = mob.getRoom().getGroundRoom();
        } else {
            room = mob.getRoom();
        }

        if (input.equalsIgnoreCase("all")) {
            Iterator<Item> itemIter = mob.getInventory().getItems().iterator();
            while (itemIter.hasNext()) {

                Item item = itemIter.next();

                room.add(item);

                mob.out("You drop an " + item.getBrief());

                checkDisease(mob, item);
                itemIter.remove();
            }
        }

        SomeMoney sm = mob.getInventory().removeCoins(input);

        if (sm != null) {
            mob.out("You drop some coins  " + sm);
            mob.getRoom().getInventory().add(sm);
            return;
        }

        Item item = mob.getInventory().remove(input);

        if (item == null) {
            mob.out("You are not carrying a " + input);
            return;
        }

        if (item instanceof Torch) {
            Torch torch = (Torch) item;
            torch.setMsgable(mob.getRoom());
        }

        room.add(item);

        mob.out("You drop an " + item.getBrief());

        checkDisease(mob, item);
    }

    private void checkDisease(Mob mob, Item item) {
        if (mob.getMobAffects().getDiseases() == null) {
            return;
        }

        for (Disease disease : mob.getMobAffects().getDiseases()) {
            if (disease.isIndirectContact()) {
                if (DiceRoll.ONE_D100.rollLessThanOrEqualTo(disease.getInfectionRate())) {
                    Disease infection = (Disease) disease.clone();
                    item.setDisease(infection);
                }
            }
        }

    }

}
