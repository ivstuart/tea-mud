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

import com.ivstuart.tmud.server.LaunchMud;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

/**
 * Created by Ivan on 30/08/2016.
 */
public class UnpossessTest {

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
    public void testUnpossess() {
        Race human = new Race();
        World.getInstance().addToWorld(human);
        Mob mob = TestHelper.makeDefaultPlayerMob("ted");
        mob.getPlayer().setAdmin(true);

        Mob sheepMob = new Mob();
        sheepMob.setNameAndId("sheep");
        sheepMob.setAlias("sheep");
        sheepMob.setHp("50");

        Room whiteRoom = TestHelper.getPortalAndClearMobs();
        whiteRoom.add(sheepMob);
        whiteRoom.add(mob);
        sheepMob.setRoom(whiteRoom);
        mob.setRoom(whiteRoom);

        Possess possess = new Possess();
        possess.execute(mob, sheepMob.getAlias());

        assertEquals("Sheep is possessed", mob, sheepMob.getPossessed());

        sheepMob.out("Message");

        Unpossess unpossess = new Unpossess();
        unpossess.execute(mob, sheepMob.getAlias());

        assertNull("Sheep is not possessed", sheepMob.getPossessed());
    }
}
