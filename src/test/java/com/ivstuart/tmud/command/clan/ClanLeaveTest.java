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

package com.ivstuart.tmud.command.clan;

import com.ivstuart.tmud.exceptions.MudException;
import com.ivstuart.tmud.person.ClanMembership;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.player.Race;
import com.ivstuart.tmud.utils.TestHelper;
import com.ivstuart.tmud.world.Clan;
import com.ivstuart.tmud.world.Clans;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

/**
 * Created by Ivan on 30/08/2016.
 */
public class ClanLeaveTest {

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
    public void testClanLeave() {
        Race human = new Race();
        World.getInstance().addToWorld(human);

        Mob mob = TestHelper.makeDefaultPlayerMob("ted");
        Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");

        try {
            World.addPlayer(player1Mob);
        } catch (MudException e) {
            e.printStackTrace();
        }

        ClanMembership clanMembership = new ClanMembership();
        clanMembership.setClanId(0);
        clanMembership.setLevel(5);
        mob.getPlayer().setClanMembership(clanMembership);

        Clans.init();
        Clan clan = new Clan();
        clan.setClanId(0);
        clan.setAlignment(true);
        Clans.addClan(clan);

        ClanAdd clanAdd = new ClanAdd();
        clanAdd.execute(mob, player1Mob.getName());

        assertEquals("Player1 joins the clan", 0, player1Mob.getPlayer().getClanMembership().getClanId());

        ClanLeave clanLeave = new ClanLeave();
        clanLeave.execute(player1Mob, "yes");

        assertNull("Player1 leaves the clan", player1Mob.getPlayer().getClanMembership());

    }
}
