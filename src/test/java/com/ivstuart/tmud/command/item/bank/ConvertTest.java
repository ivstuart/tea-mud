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

package com.ivstuart.tmud.command.item.bank;

import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.mobs.Banker;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.places.Room;
import com.ivstuart.tmud.utils.TestHelper;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ConvertTest {

    @Test
    public void testConvert50Copper() {

        Room room = TestHelper.getPortalAndClearMobs();
        room.setId("A room");

        Banker banker = new Banker();

        Mob mob = new Mob();
        mob.setRoom(room);
        room.add(mob);
        room.add(banker);

        SomeMoney coins = new Money(Money.COPPER, 50);
        mob.getInventory().add(coins);


        Convert convert = new Convert();
        convert.execute(mob, "all");

        assertEquals("Check has 5 silver", "5 $ESilver$J coins ", mob.getInventory().getPurseString());
    }

    @Test
    public void testConvert1234Copper() {

        Room room = TestHelper.getPortalAndClearMobs();
        room.setId("A room");

        Banker banker = new Banker();

        Mob mob = new Mob();
        mob.setRoom(room);
        room.add(mob);
        room.add(banker);

        SomeMoney coins = new Money(Money.COPPER, 1234);
        mob.getInventory().add(coins);


        Convert convert = new Convert();
        convert.execute(mob, "all");

        assertEquals("Check has 1234 copper", "1 $EPlatinum$J coin 2 $BGold$J coins 3 $ESilver$J coins 4 $CCopper$J coins ", mob.getInventory().getPurseString());

        convert.execute(mob, "all copper");

        assertEquals("Check has 1234 copper", "1234 $CCopper$J coins ", mob.getInventory().getPurseString());

    }

}
