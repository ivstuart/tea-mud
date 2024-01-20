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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SearchTest {

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
    public void testHideSearchMob() {

        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");

        Ability ability = new Ability("searching", 100);
        player1Mob.getLearned().add(ability);
        World.add(new BaseSkill("searching"));

        Ability hide1 = new Ability("hide", 100);
        player1Mob.getLearned().add(hide1);
        World.add(new BaseSkill("hide"));

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

        Command hide = new Hide();
        hide.execute(player1Mob, sheepMob.getAlias());

        assertTrue("sheepMob should be hidden", sheepMob.isHidden());

        Command search = new Search();

        search.execute(player1Mob, null);

        assertFalse("sheepMob should be hidden", sheepMob.isHidden());

    }

    @Test
    public void testHideSearchExit() {

        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");

        Ability ability = new Ability("searching", 100);
        player1Mob.getLearned().add(ability);
        World.add(new BaseSkill("searching"));

        Ability hide1 = new Ability("hide", 100);
        player1Mob.getLearned().add(hide1);
        World.add(new BaseSkill("hide"));

        Race human = new Race();
        World.getInstance().addToWorld(human);

        Room whiteRoom = new Room();
        whiteRoom.setId("room-01");
        Exit hiddenExit = new Exit("gate", whiteRoom.getId());
        hiddenExit.setHidden(true);
        whiteRoom.add(hiddenExit);

        whiteRoom.add(player1Mob);

        Command hide = new Hide();
        hide.execute(player1Mob, "gate");

        assertTrue("Gate should be hidden", hiddenExit.isHidden());

        Command search = new Search();

        search.execute(player1Mob, null);

        assertFalse("Gate should not be hidden", hiddenExit.isHidden());

    }
}
