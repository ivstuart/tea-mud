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

package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Race;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.utils.TestHelper;
import com.ivstuart.tmud.world.World;
import com.ivstuart.tmud.world.WorldTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Ivan on 20/09/2016.
 */
public class StealerTest {

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
    public void testStealer() {
        Race human = new Race();
        World.getInstance().addToWorld(human);
        Room whiteRoom = new Room();
        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");
        whiteRoom.add(player1Mob);
        player1Mob.setRoom(whiteRoom);

        Mob sheepMob = new Mob();
        sheepMob.setNameAndId("sheep");
        sheepMob.setAlias("sheep");
        sheepMob.setHp("500");
        sheepMob.setMv("500");
        sheepMob.setBehaviour("Stealer");


        BaseBehaviour baseBehaviour = BehaviourFactory.create("Stealer");
        baseBehaviour.setMob(sheepMob);
        baseBehaviour.setParameter(100);

        sheepMob.addTickable(baseBehaviour);
        WorldTime.addTickable(sheepMob);


        whiteRoom.add(sheepMob);
        sheepMob.setRoom(whiteRoom);


        SomeMoney coins = new Money(Money.COPPER, 20);
        player1Mob.getInventory().add(coins);

        baseBehaviour.tick();
        baseBehaviour.tick();
        baseBehaviour.tick();

        assertEquals("sheep should have 15 copper", 15, sheepMob.getInventory().getPurse().getValue());


    }


}
