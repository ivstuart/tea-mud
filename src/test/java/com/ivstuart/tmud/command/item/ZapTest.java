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

package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.*;
import com.ivstuart.tmud.utils.TestHelper;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertEquals;

public class ZapTest {

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
    public void testZap() {
        Race human = new Race();
        World.getInstance().addToWorld(human);

        Room room = new Room();
        room.setId("A room");

        Mob mob = TestHelper.makeDefaultPlayerMob("ivan");
        mob.setRoom(room);
        room.add(mob);
        TestHelper.equipWand(mob);

        Ability ability = new Ability("wands", 100);
        mob.getLearned().add(ability);
        World.add(new BaseSkill("wands"));

        Spell fireball = new Spell();
        fireball.setDamage("50");
        fireball.setId("fireball");
        fireball.setMana("COMMON");
        World.add(fireball);

        Mob sheepMob = new Mob();
        sheepMob.setNameAndId("sheep");
        sheepMob.setAlias("sheep");
        sheepMob.setHp("2d10+50");
        room.add(sheepMob);

        Zap zap = new Zap();
        zap.execute(mob, "sheep");

        mob.getFight().getFightActions().getFirst().begin();
        mob.getFight().getFightActions().getFirst().happen();

        assertEquals("Check targeting sheep", sheepMob, mob.getFight().getTarget());


    }


}
