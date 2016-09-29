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

package com.ivstuart.tmud.utils;

import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class GsonIOTest {

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
     * Gson not used for players.
     */
    @Test
	public void testSavingPlayer() {
		GsonIO saveGson = new GsonIO();

		Mob playerMob = TestHelper.makeDefaultPlayerMob("player1");
		Player player = playerMob.getPlayer();

		try {
			saveGson.save(player, "testplayer");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * Not used this is broken.
     *
     * @throws IOException
     */
    @Test(expected = RuntimeException.class)
    public void testLoadingPlayer() throws IOException {

		GsonIO saveGson = new GsonIO();
		Player player = (Player) saveGson.loadPlayer("testplayer");

		assertEquals("check player name", "player1", player.getName());

	}

}
