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
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.utils.TestHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;

public class WanderTest {

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

    /**
     *
     */
    @Test
    public void testMoveRandomly() {

        Mob mob = new Mob();
        mob.setNameAndId("wanderingMob");
        mob.setHp("500");
        Wander wandering = new Wander();

        wandering.setMob(mob);
        Room startRoom = TestHelper.makeRoomGrid();
        mob.setRoom(startRoom);
        startRoom.add(mob);

        wandering.tick();
        wandering.tick();
        wandering.tick();
        wandering.tick();

        System.out.println("Rooms been to " + wandering.getRooms());


    }

    /**
     * [ ]-[ ]-[ ]-[ start pos ]-[ ]-[ ]
     * <p>
     * Confirm that the lost sheep can get at most 2 rooms away from start
     * position. Not a deterministic test just very likely to find any issue.
     */
    @Test
    public void testMoveWithinMaxDistanceOfStartPosition() {
        Mob mob = new Mob();
        mob.setNameAndId("wanderingMob");
        mob.setHp("500");

        Wander wandering = new Wander();

        wandering.setMob(mob);
        wandering.setParameter(100);

        Room startRoom = TestHelper.makeRoomGrid();
        mob.setRoom(startRoom);
        startRoom.add(mob);

        for (int i = 0; i < 100; i++) {
            wandering.tick();
        }

        assertTrue("Rooms been to less than 3", wandering.getRooms().size() < 3);

    }

}
