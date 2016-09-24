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

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.utils.TestHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class ShowRaceTest {

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
    public void testShowRace() {

        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");
        player1Mob.getPlayer().setAdmin(true);

        Load load = new Load();
        load.execute(player1Mob, "races.txt");

        Command showRace = new ShowRace();
        showRace.execute(player1Mob, "8");

        //assertNotNull("Sheep is in room", whiteRoom.getMob("sheep"));

    }

}
