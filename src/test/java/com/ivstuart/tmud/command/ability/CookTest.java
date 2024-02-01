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

package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.items.Food;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.items.Prop;
import com.ivstuart.tmud.state.places.Room;
import com.ivstuart.tmud.utils.TestHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertFalse;

/**
 * Created by Ivan on 22/09/2016.
 */
public class CookTest {

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
    public void testCook() {


        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");
        Prop fire = TestHelper.makeFireProp("fire");

        Room whiteRoom = TestHelper.getPortalAndClearMobs();

        whiteRoom.add(fire);
        whiteRoom.add(player1Mob);

        Command cook = new Cook();

        Food food = new Food();
        food.setCookable(true);
        food.setPortions(3);
        food.setAlias("food");

        player1Mob.getInventory().add(food);

        cook.execute(player1Mob, food.getAlias());

        assertFalse("Food cooked", food.isCookable());

    }
}
