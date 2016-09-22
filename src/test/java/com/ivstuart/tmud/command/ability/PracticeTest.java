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

package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.Command;
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

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Ivan on 22/09/2016.
 */
public class PracticeTest {
    public static final String MAGIC_MISSILE = "magic missile";
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
    public void testPractice() {

        Ability ability = new Ability(MAGIC_MISSILE, 100);
        World.add(new BaseSkill(MAGIC_MISSILE));

        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");
        Prop teacher = TestHelper.makeTeacherProp("teacher");
        Room whiteRoom = new Room();

        whiteRoom.add(teacher);
        whiteRoom.add(player1Mob);

        Command learn = new Learn();
        learn.execute(player1Mob, MAGIC_MISSILE);

        player1Mob.getPlayer().getData().setPracs(100);

        LOGGER.debug("Pratices:" + player1Mob.getPlayer().getData().getPracs());

        assertEquals("Has learnt magic missile", MAGIC_MISSILE, player1Mob.getLearned().getAbility(MAGIC_MISSILE).getId());

        Command practice = new Practice();
        practice.execute(player1Mob, MAGIC_MISSILE);
        practice.execute(player1Mob, MAGIC_MISSILE);
        practice.execute(player1Mob, MAGIC_MISSILE);
        practice.execute(player1Mob, MAGIC_MISSILE);
        practice.execute(player1Mob, MAGIC_MISSILE);
        assertEquals("Has praticed magic missile", 50, player1Mob.getLearned().getAbility(MAGIC_MISSILE).getSkill());


    }
}
