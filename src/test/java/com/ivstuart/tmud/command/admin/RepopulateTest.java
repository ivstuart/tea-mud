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

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Race;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.utils.TestHelper;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertNotNull;

public class RepopulateTest {

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
    public void testRepopulate() {

        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");
        player1Mob.getPlayer().setAdmin(true);

        TestHelper.equipDagger(player1Mob);

        Race human = new Race();
        World.getInstance().addToWorld(human);

        // have test resource file to load in a mob sheep and mob player
        // test files.
        Mob sheepMob = new Mob();
        sheepMob.setNameAndId("sheep");
        sheepMob.setAlias("sheep");
        sheepMob.setHp("50");

        Room whiteRoom = new Room();
        whiteRoom.setId("room-01");
        sheepMob.setRepopRoomId(whiteRoom.getId());

        whiteRoom.add(sheepMob);
        sheepMob.setRoom(whiteRoom);
        sheepMob.setRoomId(whiteRoom.getId());
        whiteRoom.add(player1Mob);

        World.add(sheepMob);
        World.add(whiteRoom);

        sheepMob.getHp().decrease(51);
        DamageManager.checkForDefenderDeath(sheepMob, sheepMob);

        Command repop = new Repopulate();
        repop.execute(player1Mob, ""); // sheepMob.getAlias());

        assertNotNull("Sheep is in room", whiteRoom.getMob("sheep"));

    }

}
