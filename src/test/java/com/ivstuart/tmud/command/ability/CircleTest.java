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
import com.ivstuart.tmud.command.combat.Kill;
import com.ivstuart.tmud.state.mobs.Ability;
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

import static org.junit.Assert.*;

public class CircleTest {

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
    public void testCircleMobAlreadyFighting() {

        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");

        TestHelper.equipDagger(player1Mob);

        // Teach rescue to player
        Ability ability = new Ability("circle", 100);
        player1Mob.getLearned().add(ability);
        World.add(new BaseSkill("circle"));

        Race human = new Race();
        World.getInstance().addToWorld(human);

        // have test resource file to load in a mob sheep and mob player
        // test files.
        Mob sheepMob = new Mob();
        sheepMob.setNameAndId("sheep");
        sheepMob.setAlias("sheep");
        sheepMob.setHp("2d10+50");

        Room whiteRoom = TestHelper.getPortalAndClearMobs();

        whiteRoom.add(sheepMob);
        whiteRoom.add(player1Mob);

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

        player1Mob.getFight().getMelee().begin();
        sheepMob.getFight().getMelee().begin();

        assertTrue("sheep and player1 will be engaged in combat", sheepMob
                .getFight().isEngaged(player1Mob));

        Command circle = new Circle();
        circle.execute(player1Mob, sheepMob.getAlias());

//		player1Mob.getFight().getFightActions().getFirst().begin();
//		player1Mob.getFight().getFightActions().getFirst().happen();

        assertTrue("No circle queued", player1Mob.getFight().getFightActions().isEmpty());

    }

    @Test
    public void testCircleMobAlreadyFightingSomeoneElse() {

        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");

        TestHelper.equipDagger(player1Mob);

        // Teach rescue to player
        Ability ability = new Ability("circle", 105);
        player1Mob.getLearned().add(ability);
        World.add(new BaseSkill("circle"));

        Race human = new Race();
        World.getInstance().addToWorld(human);

        // have test resource file to load in a mob sheep and mob player
        // test files.
        Mob sheepMob = new Mob();
        sheepMob.setNameAndId("sheep");
        sheepMob.setAlias("sheep");
        sheepMob.setHp("2d10+50");

        Room whiteRoom = TestHelper.getPortalAndClearMobs();

        whiteRoom.add(sheepMob);
        whiteRoom.add(player1Mob);

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

        player1Mob.getFight().getMelee().begin();
        sheepMob.getFight().getMelee().begin();

        assertTrue("sheep and player1 will be engaged in combat", sheepMob
                .getFight().isEngaged(player1Mob));

        // To ensure that the player is not directly engaged with the sheep
        sheepMob.getFight().changeTarget(sheepMob);

        Command circle = new Circle();
        circle.execute(player1Mob, sheepMob.getAlias());

        player1Mob.getFight().getFightActions().getFirst().begin();
        player1Mob.getFight().getFightActions().getFirst().happen();

        assertFalse("No circle queued", player1Mob.getFight().getFightActions().isEmpty());

        assertEquals("Sheep engaged to player1Mob now", player1Mob.getName(), sheepMob.getFight().getTarget().getName());

        assertTrue("sheep and player1 will be engaged in combat", player1Mob
                .getFight().isEngaged(sheepMob));
    }

    @Test
    public void testCircleMobNotFighting() {
        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");
        TestHelper.equipDagger(player1Mob);

        // Teach rescue to player
        Ability ability = new Ability("circle", 100);
        player1Mob.getLearned().add(ability);
        World.add(new BaseSkill("circle"));

        Race human = new Race();
        World.getInstance().addToWorld(human);

        // have test resource file to load in a mob sheep and mob player
        // test files.
        Mob sheepMob = new Mob();
        sheepMob.setNameAndId("sheep");
        sheepMob.setAlias("sheep");
        sheepMob.setHp("2d10+50");

        Room whiteRoom = TestHelper.getPortalAndClearMobs();

        whiteRoom.add(sheepMob);
        whiteRoom.add(player1Mob);

        assertEquals("Check sheep name", "sheep", sheepMob.getName());
        assertNotNull("Check sheep exists in the room",
                whiteRoom.getMob(sheepMob.getName()));
        assertEquals("Check sheep is in the room", sheepMob,
                whiteRoom.getMob(sheepMob.getName()));

        World.getInstance(); // Starts time.


        Command circle = new Circle();
        circle.execute(player1Mob, sheepMob.getAlias());

        assertTrue("No circle queued", player1Mob.getFight().getFightActions().isEmpty());


    }

}
