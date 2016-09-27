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

package com.ivstuart.tmud.command.item.tier;

import com.ivstuart.tmud.command.admin.Load;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.WarMaster;
import com.ivstuart.tmud.utils.TestHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertEquals;

public class TierTest {
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
    public void testTier() {

        Room room = new Room();
        room.setId("A room");

        WarMaster warMaster = new WarMaster();

        Mob mob = TestHelper.makeDefaultPlayerMob("ivan");
        mob.getPlayer().getData().incrementWarpoints(1000);

        mob.setRoom(room);
        room.add(mob);
        room.add(warMaster);

        Load load = new Load();
        load.execute(mob, "tier-armour.txt");

        Tier tier = new Tier();
        tier.execute(mob, "");
        tier.execute(mob, "");
        tier.execute(mob, "");
        tier.execute(mob, "");
        tier.execute(mob, "");


        assertEquals("Check has 5 items", 5, mob.getInventory().getItems().size());
    }


}
