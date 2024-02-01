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
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.places.Room;
import com.ivstuart.tmud.utils.TestHelper;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class DropTest {

    @Test
    public void testDropAll() {

        Room room = new Room();
        room.setId("A room");

        Mob mob = new Mob();
        mob.setRoom(room);
        room.add(mob);
        SomeMoney coins = new Money(Money.COPPER, 50);
        mob.getInventory().add(coins);
        TestHelper.inveDagger(mob);

        Drop drop = new Drop();
        drop.execute(mob, "all");

        assertEquals("Check no items", 0, mob.getInventory().getItems().size());

        assertNotNull("Check room has items", mob.getRoom().getInventory().get("dagger"));
    }


}
