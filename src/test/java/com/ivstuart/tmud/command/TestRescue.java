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

package com.ivstuart.tmud.command;

import com.ivstuart.tmud.command.combat.Kill;
import com.ivstuart.tmud.command.combat.Rescue;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.*;
import com.ivstuart.tmud.utils.TestHelper;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class TestRescue {
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
    public void testRescueWhenNotFighting() {

        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");
        Mob player2Mob = TestHelper.makeDefaultPlayerMob("player2");

        // Teach rescue to player 2
        Ability rescueAbility = new Ability("rescue", 100);
        player2Mob.getLearned().add(rescueAbility);
        World.add(new BaseSkill("rescue"));

        Race human = new Race();
        World.getInstance().addToWorld(human);

        // have test resource file to load in a mob sheep and mob player
        // test files.
        Mob sheepMob = new Mob();
        sheepMob.setNameAndId("sheep");
        sheepMob.setAlias("sheep");
        sheepMob.setHp("2d10+50");

        Room whiteRoom = new Room();

        whiteRoom.add(sheepMob);
        whiteRoom.add(player1Mob);
        whiteRoom.add(player2Mob);

        Command kill = new Kill();

        assertEquals("Check sheep name", "sheep", sheepMob.getName());
        assertNotNull("Check sheep exists in the room",
                whiteRoom.getMob(sheepMob.getName()));
        assertEquals("Check sheep is in the room", sheepMob,
                whiteRoom.getMob(sheepMob.getName()));

        World.getInstance(); // Starts time.

        kill.execute(player1Mob, sheepMob.getName());
        kill.execute(sheepMob, player1Mob.getName());

        // Check they are targeting each other.
        assertEquals("sheep should target player1", player1Mob, sheepMob
                .getFight().getTarget());
        assertEquals("player 1 should target sheep", sheepMob, player1Mob
                .getFight().getTarget());

        sleepShortWhile();

        assertTrue("sheep and player1 will be engaged in combat", sheepMob
                .getFight().isEngaged(player1Mob));

        // RESCUE
        Command rescue = new Rescue();
        rescue.execute(player2Mob, "player1");

        sleepShortWhile();

        // Check they are targeting each other, but player 2 now.
        assertEquals("sheep should target player2", player2Mob.getName(),
                sheepMob.getFight().getTarget().getName());

        assertEquals("player 2 should target sheep", sheepMob.getName(),
                player2Mob.getFight().getTarget().getName());

        assertTrue("sheep should target other player", sheepMob.getFight()
                .isEngaged(player2Mob));

        // sleepShortWhile();

        assertFalse("player 1 is no longer engaged with the sheep", player1Mob
                .getFight().isEngaged(sheepMob));

    }

    public void sleepShortWhile() {
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
