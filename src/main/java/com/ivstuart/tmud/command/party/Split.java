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

package com.ivstuart.tmud.command.party;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.command.item.Give;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.state.Mob;

import java.util.List;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Split extends BaseCommand {

    /**
     *
     */
    public Split() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob, String input) {

        String[] inputSplit = input.split(" ");

        int coins = 0;
        try {
            coins = Integer.parseInt(inputSplit[0]);
        } catch (NumberFormatException e) {
            mob.out("No number of coins specified");
        }

        Money money = new Money(Money.COPPER, coins);

        // Check has this much money available
        if (!mob.getInventory().hasMoney(money)) {
            mob.out("Nothing to split");
            return;
        }

        List<Mob> group = mob.getPlayer().getGroup();
        if (group == null) {
            mob.out("No group to split cash with");
            return;
        }

        int copperPerPerson = money.getQuantity() / group.size();

        // Give
        for (Mob aMob : group) {
            if (aMob != mob) {
                CommandProvider.getCommand(Give.class).execute(mob, copperPerPerson + " copper " + aMob.getName());
            }
        }

    }

}
