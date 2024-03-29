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

public class WithdrawTest {

    @Test
    public void testWithdraw30Copper() {

        Room room = TestHelper.getPortalAndClearMobs();
        room.setId("A room");

        Banker banker = new Banker();

        Mob mob = TestHelper.makeDefaultPlayerMob("ivan");
        mob.setRoom(room);
        room.add(mob);
        room.add(banker);

        SomeMoney coins = new Money(Money.COPPER, 50);
        mob.getInventory().add(coins);


        Deposit deposit = new Deposit();
        deposit.execute(mob, "all");

        assertEquals("Check has nothing", "", mob.getInventory().getPurseString());

        assertEquals("Check has 50 copper", "50 $CCopper$J coins ", mob.getPlayer().getBank().getPurseString());

        Withdraw withdraw = new Withdraw();
        withdraw.execute(mob, "20 copper");

        assertEquals("Check has 20 copper", "20 $CCopper$J coins ", mob.getInventory().getPurseString());

        assertEquals("Check has 30 copper", "30 $CCopper$J coins ", mob.getPlayer().getBank().getPurseString());

    }


}
