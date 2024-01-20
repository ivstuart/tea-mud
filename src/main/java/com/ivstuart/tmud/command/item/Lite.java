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
import com.ivstuart.tmud.state.Torch;
import com.ivstuart.tmud.world.WorldTime;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Lite extends BaseCommand {

    @Override
    public void execute(Mob mob_, String input_) {

        if (!mob_.getInventory().hasLighter()) {
            mob_.out("You have nothing to lite the " + input_ + " with.");
            return;
        }

        Item item = mob_.getInventory().get(input_);

        if (item == null) {
            item = (Item) mob_.getEquipment().get(input_);
        }

        if (item == null) {
            mob_.out("You are not carrying a " + input_ + " to lite.");
            return;
        }

        if (item instanceof Torch) {
            Torch torch = (Torch) item;

            if (torch.isLit()) {
                mob_.out("That is already alight!");
                return;
            }

            if (torch.getFuel() == 0) {
                mob_.out("That has already burnt out!");
                return;
            }

            torch.setLit(true);

            torch.setMsgable(mob_);

            WorldTime.addTickable(torch);

            mob_.out("You lite that ");
        } else {
            mob_.out("The " + item.getLook() + " is not litable.");
        }

    }

}
