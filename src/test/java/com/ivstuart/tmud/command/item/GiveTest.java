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

package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.places.Room;
import com.ivstuart.tmud.utils.TestHelper;
import com.ivstuart.tmud.world.World;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertEquals;

public class GiveTest {


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
    public void testGive50Copper() {

        World instance = World.getInstance();

        Room room = TestHelper.getPortalAndClearMobs();
        room.setId("A room");

        Mob mob = new Mob();
        mob.setRoom(room);
        room.add(mob);
        SomeMoney coins = new Money(Money.COPPER, 50);
        mob.getInventory().add(coins);

        Mob ste = new Mob();
        ste.setAlias("ste");
        ste.setName("Ste");
        ste.setRoom(room);
        room.add(ste);

        Give give = new Give();
        give.execute(mob, "50 copper ste");

        assertEquals("Check has 50 copper", "50 $CCopper$J coins ", ste.getInventory().getPurseString());
    }

}
