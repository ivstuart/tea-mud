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

package com.ivstuart.tmud.command.item.bank;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Deposit extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        Mob banker = mob.getRoom().getBanker();

        if (banker == null) {
            mob.out("There is no bank here to deposit into");
            return;
        }

        if (checkCashDeposit(mob, banker, input)) {
            return;
        }

        Item item = mob.getInventory().get(input);

        if (item == null) {
            mob.out("No item to deposit " + input);
            return;
        }

        if (item.isNoBank()) {
            mob.out("The bank will not accept these items");
            return;
        }

        mob.getInventory().remove(item);

        mob.getPlayer().getBank().add(item);

        mob.out("You deposit a " + item.getName());
    }

    private boolean checkCashDeposit(Mob mob, Mob banker, String input) {

        SomeMoney cash = mob.getInventory().removeCoins(input);

        if (cash == null) {
            return false;
        }

        mob.getPlayer().getBank().add(cash);

        // banker.getInventory().add(cash);

        mob.out("You deposit " + cash + " into the bank");

        return true;
    }

}
