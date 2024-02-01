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
import com.ivstuart.tmud.command.admin.LearnAll;
import com.ivstuart.tmud.command.combat.Kill;
import com.ivstuart.tmud.constants.ManaType;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.player.Race;
import com.ivstuart.tmud.state.places.Room;
import com.ivstuart.tmud.state.skills.Spell;
import com.ivstuart.tmud.utils.TestHelper;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CastTest {

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
    public void testCastingWhileNotFightingHealing() {

        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");
        player1Mob.getPlayer().setAdmin(true);

        Race human = new Race();
        World.getInstance().addToWorld(human);


        Room whiteRoom = TestHelper.getPortalAndClearMobs();
        whiteRoom.add(player1Mob);
        Spell spell = new Spell();
        spell.setId("lesser healing");
        spell.setAmount("100");
        spell.setMana(ManaType.FIRE);
        spell.setLevel(1);
        spell.setSpellEffect("HEAL");
        spell.setCost(1);

        World.add(spell);

        Command testLearning = new LearnAll();
        testLearning.execute(player1Mob, null);

        player1Mob.getHp().decrease(50);

        Command cast = new Cast();
        cast.execute(player1Mob, "lesser healing");

        player1Mob.getFight().getFightActions().getFirst().begin();
        player1Mob.getFight().getFightActions().getFirst().happen();

        assertEquals("Player should be healed", 100, player1Mob.getHp().getValue());


    }

    @Test
    public void testCastingDamageSpellSingleTarget() {

        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");
        player1Mob.getPlayer().setAdmin(true);

        TestHelper.equipDagger(player1Mob);

        Spell spell = new Spell();
        spell.setId("fire blast");
        spell.setDamage("100");
        spell.setMana(ManaType.FIRE);
        spell.setLevel(1);
        // spell.setSpellEffect("HEAL");
        spell.setCost(1);

        World.add(spell);

        Command testLearning = new LearnAll();
        testLearning.execute(player1Mob, null);

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

        Command kill = new Kill();

        Assert.assertEquals("Check sheep name", "sheep", sheepMob.getName());
        assertNotNull("Check sheep exists in the room",
                whiteRoom.getMob(sheepMob.getName()));
        Assert.assertEquals("Check sheep is in the room", sheepMob,
                whiteRoom.getMob(sheepMob.getName()));

        World.getInstance(); // Starts time.

        kill.execute(player1Mob, sheepMob.getName());
        kill.execute(sheepMob, player1Mob.getName());

        // Check they are targeting each other.
        Assert.assertEquals("sheep should target player1", player1Mob, sheepMob
                .getFight().getTarget());
        Assert.assertEquals("player 1 should target sheep", sheepMob, player1Mob
                .getFight().getTarget());

        player1Mob.getFight().getMelee().begin();
        sheepMob.getFight().getMelee().begin();

        assertTrue("sheep and player1 will be engaged in combat", sheepMob
                .getFight().isEngaged(player1Mob));

        // Override any ground fighting for test purposes as it would stop casting
        if (player1Mob.getFight().isGroundFighting()) {
            player1Mob.getFight().setMeleeToBasicAttack();
            player1Mob.getTargetFight().setMeleeToBasicAttack();
        }


        Command cast = new Cast();
        cast.execute(player1Mob, "fire blast");

        player1Mob.getFight().getFightActions().getFirst().begin();
        player1Mob.getFight().getFightActions().getFirst().happen();

        assertEquals("Sheep should be killed.", 0, sheepMob.getHp().getValue());
    }


}
