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
import com.ivstuart.tmud.person.PlayerData;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Remort extends BaseCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(Mob mob, String input) {

        int level = mob.getPlayer().getData().getLevel();

        if (level < 60) {
            mob.out("You must be level 60 to remort!");
            return;
        }

        int remorts = mob.getPlayer().getData().getRemorts();

        if (remorts > 5) {
            mob.out("You may only remort 6 times!");
            return;
        }

        String[] params = input.split(" ", 6);

        if (params.length != 6) {
            mob.out("Remort [str] [con] [dex] [int] [wis] yes");
            mob.out(" i.e. Remort 0 3 1 1 1 yes");
            return;
        }

        if (!params[5].equals("yes")) {
            mob.out("Last parameter for command must be yes");
            return;
        }
        int str, con, dex, intelligence, wis;

        try {
            str = Integer.parseInt(params[0]);
            con = Integer.parseInt(params[1]);
            dex = Integer.parseInt(params[2]);
            intelligence = Integer.parseInt(params[3]);
            wis = Integer.parseInt(params[4]);
        } catch (NumberFormatException nfe) {
            mob.out("Parameters for stats to increase must be numerics");
            LOGGER.error("Bad input for remort", nfe);
            return;
        }

        int totalPoint = str + con + dex + intelligence + wis;

        if (totalPoint != 6) {
            mob.out(" You have exactly 6 points to spend.");
            return;
        }

        mob.getMobAffects().removeAll();

        mob.getPlayer().getAttributes().getSTR().increaseToMaximum(str);
        mob.getPlayer().getAttributes().getCON().increaseToMaximum(con);
        mob.getPlayer().getAttributes().getDEX().increaseToMaximum(dex);
        mob.getPlayer().getAttributes().getINT().increaseToMaximum(intelligence);
        mob.getPlayer().getAttributes().getWIS().increaseToMaximum(wis);

        mob.setLevel(1);
        mob.getPlayer().getData().setLevel(1);
        mob.setHp("50");
        mob.setMv("50");
        mob.setMana(50);


        mob.getPlayer().getData().setRemort(++remorts);


        int playerInt = mob.getPlayer().getAttributes().getINT().getValue();
        int wisdom = mob.getPlayer().getAttributes().getWIS().getValue();

        mob.getMana().setCastLevel((playerInt + wisdom) / 2);

        PlayerData data = mob.getPlayer().getData();
        data.setThirst(500);
        data.setHunger(500);

        mob.out("You feel yourself become less experienced as you remort but more powerful");

        World.getMudStats().addNumberOfRemorts();

    }

}
