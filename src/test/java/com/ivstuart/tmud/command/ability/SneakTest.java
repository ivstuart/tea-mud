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
import com.ivstuart.tmud.person.movement.MoveManager;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.mobs.Ability;
import com.ivstuart.tmud.state.skills.BaseSkill;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.places.Room;
import com.ivstuart.tmud.utils.TestHelper;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class SneakTest {

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
    public void testSneak() {

        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");

        Ability ability = new Ability("sneak", 100);
        player1Mob.getLearned().add(ability);
        World.add(new BaseSkill("sneak"));

        Room startRoom = TestHelper.makeRoomGrid();
        player1Mob.setRoom(startRoom);
        startRoom.add(player1Mob);

        Command sneak = new Sneak();
        sneak.execute(player1Mob, null);

        Mob sheepMob = new Mob();
        sheepMob.setNameAndId("sheep");
        sheepMob.setAlias("sheep");
        sheepMob.setHp("2d10+50");

        startRoom.add(sheepMob);

        MoveManager.random(player1Mob);
        MoveManager.random(player1Mob);
        MoveManager.random(player1Mob);
        MoveManager.random(player1Mob);

    }


}
