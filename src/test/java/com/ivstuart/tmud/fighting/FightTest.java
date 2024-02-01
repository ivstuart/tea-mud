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

package com.ivstuart.tmud.fighting;

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

public class FightTest {

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
    public void testFight() {
        Race human = new Race();
        World.getInstance().addToWorld(human);

        Room room = TestHelper.makeRoomGrid();
        room.setId("A room");

        Mob mob = TestHelper.makeDefaultPlayerMob("Ivan");
        Mob ste = TestHelper.makeDefaultPlayerMob("Ste");
        mob.setRoom(room);
        room.add(mob);
        room.add(ste);
        ste.setRoom(room);

        Fight.startCombat(mob, ste);

        assertEquals("Targeting each other", mob, ste.getFight().getTarget());
        assertEquals("Targeting each other", ste, mob.getFight().getTarget());

        mob.getFight().getMelee().begin();
        mob.getFight().getMelee().happen();

    }


}
