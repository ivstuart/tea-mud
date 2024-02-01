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

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.command.combat.Kill;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.player.Race;
import com.ivstuart.tmud.state.places.Room;
import com.ivstuart.tmud.utils.TestHelper;
import com.ivstuart.tmud.world.World;
import com.ivstuart.tmud.world.WorldTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ivan on 20/09/2016.
 */
public class AssistTest {

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
    public void testAssist() {
        Race human = new Race();
        World.getInstance().addToWorld(human);

        // have test resource file to load in a mob sheep and mob player
        // test files.
        Mob sheepMob = new Mob();
        sheepMob.setNameAndId("sheep");
        sheepMob.setAlias("sheep");
        sheepMob.setHp("500");
        sheepMob.setBehaviour("Assist:100");
        sheepMob.getHp().deduct(300);

        BaseBehaviour baseBehaviour = BehaviourFactory.create("Assist");
        baseBehaviour.setMob(sheepMob);
        baseBehaviour.setParameter(100);

        sheepMob.addTickable(baseBehaviour);
        WorldTime.addTickable(sheepMob);

        Mob sheepMob2 = new Mob(sheepMob);

        Room whiteRoom = TestHelper.getPortalAndClearMobs();
        whiteRoom.add(sheepMob);
        sheepMob.setRoom(whiteRoom);
        whiteRoom.add(sheepMob2);
        sheepMob2.setRoom(whiteRoom);

        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");
        whiteRoom.add(player1Mob);
        player1Mob.setRoom(whiteRoom);

        Command kill = new Kill();
        kill.execute(player1Mob, sheepMob.getAlias());
        player1Mob.getFight().getMelee().begin();

        //baseBehaviour.tick();
        sheepMob2.tick();

        assertEquals("sheep should be fighting", player1Mob, sheepMob.getFight().getTarget());
        assertEquals("sheep2 should be fighting", player1Mob, sheepMob2.getFight().getTarget());
    }
}
