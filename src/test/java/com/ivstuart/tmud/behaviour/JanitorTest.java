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

import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.Weapon;
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
public class JanitorTest {
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
    public void testJanitor() {

        Mob sheepMob = new Mob();
        sheepMob.setNameAndId("sheep");
        sheepMob.setAlias("sheep");
        sheepMob.setHp("500");
        sheepMob.setBehaviour("Janitor");
        sheepMob.getHp().deduct(300);

        BaseBehaviour baseBehaviour = BehaviourFactory.create("Janitor");
        baseBehaviour.setMob(sheepMob);
        baseBehaviour.setParameter(100);

        sheepMob.addTickable(baseBehaviour);


        Room whiteRoom = new Room();
        whiteRoom.add(sheepMob);
        sheepMob.setRoom(whiteRoom);


        Item weapon = new Weapon();
        weapon.setId("weapon-01");
        weapon.setAlias("sword");
        weapon.setBrief("a sword");
        whiteRoom.add(weapon);

        baseBehaviour.tick();


        assertEquals("sheep should be carrying", weapon, sheepMob.getInventory().getItems().get(0));
    }
}
