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

package com.ivstuart.tmud.command.misc;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.mobs.ProfessionMaster;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Join extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        ProfessionMaster professionMaster = mob.getRoom().getProfessionMaster();

        if (professionMaster == null) {
            mob.out("There is no one here to join a profession with");
            return;
        }

        if (mob.getPlayer().getProfession() != null) {
            mob.out("You already have a profession");
            return;
        }

        // 88 gold to join
        SomeMoney cash = mob.getInventory().removeCoins("88 gold");

        if (cash == null || cash.getValue() < 8800) {
            mob.out("You must have 88 gold coins to join");
            return;
        }

        mob.out("You join the " + professionMaster.getProf() + " profession, welcome.");
        mob.getPlayer().setProfession(professionMaster.getProf());


    }

}
