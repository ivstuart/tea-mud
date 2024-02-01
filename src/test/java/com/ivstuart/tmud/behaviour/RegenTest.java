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

package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.player.Race;
import com.ivstuart.tmud.state.places.Room;
import com.ivstuart.tmud.world.World;
import com.ivstuart.tmud.world.WorldTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class RegenTest {
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
    public void testRegen() {
        Race human = new Race();
        World.getInstance().addToWorld(human);

        // have test resource file to load in a mob sheep and mob player
        // test files.
        Mob sheepMob = new Mob();
        sheepMob.setNameAndId("sheep");
        sheepMob.setAlias("sheep");
        sheepMob.setHp("500");
        sheepMob.setBehaviour("Regen");
        sheepMob.getHp().deduct(300);

        BaseBehaviour baseBehaviour = BehaviourFactory.create("Regen");
        baseBehaviour.setMob(sheepMob);
        baseBehaviour.setParameter(100);
        baseBehaviour.setParameter2(50);

        sheepMob.addTickable(baseBehaviour);
        WorldTime.addTickable(sheepMob);

        Room whiteRoom = World.getPortal();
        whiteRoom.add(sheepMob);
        sheepMob.setRoomLocation(whiteRoom.getRoomLocation());

        baseBehaviour.tick();
        baseBehaviour.tick();
        baseBehaviour.tick();
        baseBehaviour.tick();
        baseBehaviour.tick();
        baseBehaviour.tick();

        assertEquals("sheep should be at full health", 500, sheepMob.getHp().getValue());
    }

}
