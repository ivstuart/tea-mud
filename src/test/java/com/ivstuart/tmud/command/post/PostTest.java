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

package com.ivstuart.tmud.command.post;

import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Prop;
import com.ivstuart.tmud.state.Race;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.utils.TestHelper;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;

public class PostTest {

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
    public void testPost() {
        Race human = new Race();
        World.getInstance().addToWorld(human);

        Room room = TestHelper.makeRoomGrid();
        room.setId("A room");

        Mob mob = TestHelper.makeDefaultPlayerMob("Ivan");
        Mob ste = TestHelper.makeDefaultPlayerMob("Ste");
        mob.setRoom(room);
        room.add(mob);
        room.add(ste);
        ste.setRoom(room);

        TestHelper.inveDagger(mob);

        SomeMoney cash = new Money(Money.COPPER, 100);
        mob.getInventory().add(cash);

        Prop prop = new Prop();
        prop.setId("postbox-01");
        prop.setProperties("postbox");
        prop.setAlias("post box");

        room.add(prop);

        Post post = new Post();
        post.execute(mob, "dagger Ste");

        assertNull("Check dagger gone from Ivan", mob.getInventory().get("dagger"));

        post.execute(ste, "");

        assertNotNull("Check dagger gone to Ste", ste.getInventory().get("dagger"));
    }


}
