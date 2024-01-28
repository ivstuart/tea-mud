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
import com.ivstuart.tmud.state.Scroll;
import com.ivstuart.tmud.utils.StringUtil;

public class Recite extends BaseCommand {

    /**
     * @param input
     * @return
     */


    @Override
    public void execute(Mob mob, String input) {

        Item item = mob.getInventory().get(StringUtil.getFirstWord(input));

        if (item == null) {
            mob.out("You can not find a " + input + " to recite");
            return;
        }

        if (!item.isRecitable()) {
            mob.out("You can not recite a " + item.getName());
            return;
        }

        Scroll scroll = null;
        if (item instanceof Scroll) {
            scroll = (Scroll) item;
        }

        if (scroll == null) {
            mob.out("The item " + item.getName() + " is not a scroll");
            return;
        }

        scroll.recite(mob, input);

        mob.getInventory().getItems().remove(scroll);

        mob.out("The scroll crumbles to dust the energies used up");

    }


}
