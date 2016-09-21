/*
 *  Copyright 2016. Ivan Stuart
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

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.command.combat.Kill;
import com.ivstuart.tmud.person.movement.MoveManager;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;

public class TrackerTest {

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
    public void testMoveSamePath() {
        Race human = new Race();
        World.getInstance().addToWorld(human);

        Mob mob = new Mob();
        mob.setNameAndId("TrackingMob");
        mob.setAlias("TrackingMob");
        mob.setHp("500");


        Tracker tracker = new Tracker();
        tracker.setParameter(100);

        tracker.setMob(mob);
        Room startRoom = TestHelper.makeRoomGrid();
        mob.setRoom(startRoom);
        startRoom.add(mob);

        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");
        startRoom.add(player1Mob);
        player1Mob.setRoom(startRoom);

        Command kill = new Kill();
        kill.execute(player1Mob, mob.getAlias());
        kill.execute(mob, player1Mob.getAlias());

        tracker.tick();

        // Flee and end combat
        MoveManager.random(player1Mob);
        mob.getFight().stopFighting();

        LOGGER.debug("Tracks:" + startRoom.getTracks().toString());

        tracker.tick();
        tracker.tick();
        tracker.tick();

        assertNotSame("Should not be in start room", startRoom, player1Mob.getRoom());

        assertEquals("Should be in the same room", player1Mob.getRoom(), mob.getRoom());


    }

}
