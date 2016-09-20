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

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.command.combat.Kill;
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
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Ivan on 20/09/2016.
 */
public class RetargetTest {

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
    public void testRetarget() {
        Race human = new Race();
        World.getInstance().addToWorld(human);

        // have test resource file to load in a mob sheep and mob player
        // test files.
        Mob sheepMob = new Mob();
        sheepMob.setNameAndId("sheep");
        sheepMob.setAlias("sheep");
        sheepMob.setHp("500");
        sheepMob.setBehaviour("Retarget");
        sheepMob.getHp().deduct(300);

        BaseBehaviour baseBehaviour = BehaviourFactory.create("Retarget");
        baseBehaviour.setMob(sheepMob);
        baseBehaviour.setParameter(100);

        sheepMob.addTickable(baseBehaviour);
        WorldTime.addTickable(sheepMob);

        Room whiteRoom = new Room();
        whiteRoom.add(sheepMob);
        sheepMob.setRoom(whiteRoom);

        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");
        whiteRoom.add(player1Mob);
        player1Mob.setRoom(whiteRoom);

        Mob player2Mob = TestHelper.makeDefaultPlayerMob("player2");
        whiteRoom.add(player2Mob);
        player2Mob.setRoom(whiteRoom);

        List<Mob> players = new ArrayList<>();
        players.add(player1Mob);
        players.add(player2Mob);

        player2Mob.setFollowing(player1Mob);
        player1Mob.getPlayer().setGroup(players);

        Command kill = new Kill();
        kill.execute(player1Mob, sheepMob.getAlias());
        player1Mob.getFight().getMelee().begin();
        kill.execute(player2Mob, sheepMob.getAlias());
        player2Mob.getFight().getMelee().begin();

        baseBehaviour.tick();

        assertTrue("sheep should be fighting", player2Mob.getName().equals(sheepMob.getFight().getTarget().getName()) ||
                player1Mob.getName().equals(sheepMob.getFight().getTarget().getName()));
    }


}
