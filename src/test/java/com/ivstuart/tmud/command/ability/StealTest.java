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

package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.mobs.Ability;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.places.Room;
import com.ivstuart.tmud.state.player.Race;
import com.ivstuart.tmud.state.skills.BaseSkill;
import com.ivstuart.tmud.utils.TestHelper;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;

public class StealTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Before
    public void setUp() {

        try {
            LaunchMud.loadMudServerProperties();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSteal() {

        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");

        Ability ability = new Ability("steal", 105);
        player1Mob.getLearned().add(ability);
        World.add(new BaseSkill("steal"));

        Race human = new Race();
        World.getInstance().addToWorld(human);

        // have test resource file to load in a mob sheep and mob player
        // test files.
        Mob sheepMob = new Mob();
        sheepMob.setNameAndId("gnome");
        sheepMob.setAlias("gnome");
        sheepMob.setHp("2d10+50");

        SomeMoney cash = new Money(Money.COPPER, 100);
        sheepMob.getInventory().add(cash);

        Room whiteRoom = World.getPortal();

        whiteRoom.add(sheepMob);
        whiteRoom.add(player1Mob);

        assertTrue("Check gnome has cash", sheepMob.getInventory().getPurse().getValue() > 0);

        // Bash
        Command steal = new Steal();
        steal.execute(player1Mob, sheepMob.getAlias());

        assertTrue("Player should have some cash", player1Mob.getInventory().getPurse().getValue() > 0);


    }


}
