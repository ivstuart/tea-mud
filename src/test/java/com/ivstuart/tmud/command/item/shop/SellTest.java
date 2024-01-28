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

package com.ivstuart.tmud.command.item.shop;

import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.ShopKeeper;
import com.ivstuart.tmud.utils.TestHelper;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SellTest {

    @Test
    public void testSellDagger() {

        Room room = new Room();
        room.setId("A room");

        ShopKeeper shopKeeper = new ShopKeeper();
        SomeMoney coins = new Money(Money.COPPER, 50);
        shopKeeper.getInventory().add(coins);

        Mob mob = TestHelper.makeDefaultPlayerMob("ivan");
        TestHelper.inveDagger(mob);
        mob.setRoom(room);
        room.add(mob);
        room.add(shopKeeper);

        Sell sell = new Sell();
        sell.execute(mob, "dagger");

        assertEquals("Check has 10 copper", "10 $CCopper$J coins ", mob.getInventory().getPurseString());

        assertNotNull("Check has the dagger", shopKeeper.getInventory().get("dagger"));
    }


}
