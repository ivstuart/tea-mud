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

package com.ivstuart.tmud.command.communication;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Race;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.RoomEnum;
import com.ivstuart.tmud.utils.TestHelper;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class AuctionItemTest {

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
    public void testAuctionItem() {

        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");
        TestHelper.inveDagger(player1Mob);

        SomeMoney cash = new Money(Money.COPPER, 1000);
        player1Mob.getInventory().add(cash);

        Race human = new Race();
        World.getInstance().addToWorld(human);

        Room startRoom = new Room();
        startRoom.setFlag(RoomEnum.AUCTION);
        startRoom.add(player1Mob);
        player1Mob.setRoom(startRoom);

        Command auction = new AuctionItem();
        auction.execute(player1Mob, "dagger");
    }


}
