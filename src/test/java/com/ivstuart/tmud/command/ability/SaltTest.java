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
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.items.Food;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.player.Race;
import com.ivstuart.tmud.state.places.Room;
import com.ivstuart.tmud.utils.TestHelper;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotNull;

public class SaltTest {

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
    public void testSalt() {

        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");

        TestHelper.equipDagger(player1Mob);

        Race human = new Race();
        World.getInstance().addToWorld(human);

        // have test resource file to load in a mob sheep and mob player
        // test files.
        Mob sheepMob = new Mob();
        sheepMob.setNameAndId("sheep");
        sheepMob.setAlias("sheep");
        sheepMob.setHp("50");

        Room whiteRoom = TestHelper.getPortalAndClearMobs();

        whiteRoom.add(sheepMob);
        whiteRoom.add(player1Mob);

        sheepMob.getHp().decrease(51);
        DamageManager.checkForDefenderDeath(sheepMob, sheepMob);

        Command butcher = new Butcher();
        butcher.execute(player1Mob, "corpse"); // sheepMob.getAlias());

        assertNotNull("No food in inventory from butchering", player1Mob.getInventory().get("food"));

        Command salt = new Salt();
        salt.execute(player1Mob, "food");

        Food food = (Food) player1Mob.getInventory().get("food");

        assertFalse("Check food salty", food.isSaltable());

    }

}
