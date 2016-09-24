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
import com.ivstuart.tmud.command.communication.Say;
import com.ivstuart.tmud.exceptions.MudException;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.utils.TestHelper;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class SnoopTest {

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
    public void testSnoop() {

        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");
        player1Mob.getPlayer().setAdmin(true);

        World.add(player1Mob);
        try {
            World.addPlayer(player1Mob);
        } catch (MudException e) {
            e.printStackTrace();
        }


        Room whiteRoom = new Room();
        whiteRoom.setId("room-01");
        whiteRoom.add(player1Mob);
        player1Mob.setRoom(whiteRoom);

        Command snoop = new Snoop();
        snoop.execute(player1Mob, player1Mob.getAlias());

        Command say = new Say();
        say.execute(player1Mob, "test");

        player1Mob.out("test");
        //assertNotNull("Sheep is in room", whiteRoom.getMob("sheep"));

    }

}
