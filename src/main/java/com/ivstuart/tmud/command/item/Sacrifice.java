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
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.items.Corpse;
import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.items.Prop;
import com.ivstuart.tmud.utils.MudArrayList;
import com.ivstuart.tmud.utils.StringUtil;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sacrifice extends BaseCommand {

    /**
     * @param input
     * @return
     */
    private boolean checkSacCash(Mob mob, String input) {
        SomeMoney sm = mob.getRoom().getInventory().removeCoins(input);

        if (sm != null) {
            mob.out("You sacrifice " + sm);
            return true;
        }

        return false;
    }

    @Override
    public void execute(Mob mob, String input) {

        if (checkSacCash(mob, input)) {
            return;
        }

        String lastWord = StringUtil.getLastWord(input);
        Prop prop = mob.getRoom().getProps().get(lastWord);

        if (prop != null) {
            if (prop instanceof Corpse) {
                Corpse corpse = (Corpse) prop;
                mob.getRoom().remove(corpse);
                mob.out("You sacrifice an " + corpse.getName());
                return;
            }
        }

        MudArrayList<Item> items = mob.getRoom().getInventory().getItems();

        if (items == null) {
            mob.out(input + " is not here to sacrifice!");
            return;
        }

        Item anItem = items.remove(input);

        if (anItem == null) {
            mob.out("Can not sacrifice " + input + " it is not here!");
            return;
        }

        mob.out("You sacrifice an " + anItem.getBrief());
    }

}
