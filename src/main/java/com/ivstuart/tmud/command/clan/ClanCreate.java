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

package com.ivstuart.tmud.command.clan;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.ClanMembership;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.Clan;
import com.ivstuart.tmud.world.Clans;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ClanCreate extends BaseCommand {

    /**
     *
     */
    public ClanCreate() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob, String input) {

        SomeMoney cash = mob.getInventory().getPurse().removeAndConvert(10000); // 100000000;
        if (cash == null) {
            mob.out("You do not have sufficient funds 10000 copper to create a clan");
            return;
        }
        mob.getInventory().setPurse(cash);

        Clan clan = new Clan();
        int clanId = Clans.getClans().size();
        clan.setClanId(clanId);

        clan.addMembers(mob.getPlayer().getName());
        clan.setLeader(mob.getPlayer().getName());
        clan.setDonateRoom(mob.getRoomId());
        clan.setName(input);
        clan.setAlignment(mob.isGood());

        ClanMembership clanMembership = new ClanMembership();
        clanMembership.setClanId(clanId);
        clanMembership.setLevel(10);

        mob.getPlayer().setClanMembership(clanMembership);


        mob.out("You create a new clan called: " + input);
        Clans.addClan(clan);


    }

}
