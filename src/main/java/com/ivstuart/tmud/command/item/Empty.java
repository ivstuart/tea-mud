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

package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Waterskin;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Empty extends BaseCommand {

    @Override
    public void execute(Mob mob_, String input_) {

        Item item = mob_.getInventory().get(input_);

        if (item == null) {
            item = (Item) mob_.getEquipment().get(input_);
        }

        if (item == null) {
            mob_.out("You are not carrying a " + input_ + " to empty.");
            return;
        }

        if (item instanceof Waterskin) {
            Waterskin waterskin = (Waterskin) item;

            if (waterskin.getDrafts() == 0) {
                mob_.out("You empty this but it was already empty");
                return;
            }

            mob_.out("You empty some liquid from " + waterskin);

            waterskin.empty();
        } else {
            mob_.out("The " + item.getLook() + " will not empty.");
        }

    }

}
