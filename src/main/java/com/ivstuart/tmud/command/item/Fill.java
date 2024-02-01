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
import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.items.Prop;
import com.ivstuart.tmud.state.items.Waterskin;
import com.ivstuart.tmud.utils.StringUtil;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Fill extends BaseCommand {

    /**
     * Fill waterskin from fountain
     * Fill waterskin fountain
     * Fill waterskin
     *
     * @param mob_
     * @param input_
     */
    @Override
    public void execute(Mob mob_, String input_) {

        String target = StringUtil.getLastWord(input_);
        // Check for fountain first of all
        Prop fountain = mob_.getRoom().getProps().get(target);

        if (fountain == null || !fountain.isWaterSource()) {
            mob_.out("That is not a water source");
            return;
        }

        String container = StringUtil.getFirstWord(input_);

        Item item = mob_.getInventory().get(container);

        if (item == null) {
            item = (Item) mob_.getEquipment().get(container);
        }

        if (item == null) {
            mob_.out("You are not carrying a " + container + " to fill.");
            return;
        }

        if (item instanceof Waterskin) {
            Waterskin waterskin = (Waterskin) item;

            mob_.out("You fill a " + waterskin.getLook());

            waterskin.fill();
        } else {
            mob_.out("The " + item.getLook() + " is not fillable.");
        }

    }

}
