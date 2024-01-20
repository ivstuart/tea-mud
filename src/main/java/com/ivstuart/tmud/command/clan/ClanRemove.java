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
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.Clans;
import com.ivstuart.tmud.world.World;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ClanRemove extends BaseCommand {

    /**
     *
     */
    public ClanRemove() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob, String input) {

        ClanMembership clanMembership = mob.getPlayer().getClanMembership();

        if (clanMembership == null) {
            mob.out("You are not in a clan");
            return;
        }

        if (clanMembership.getLevel() < 5) {
            mob.out("You need to be more senior in your clan to remove people");
        }

        Player initiate = World.getPlayer(input);

        if (initiate == null) {
            mob.out("There is no player by the name " + input + " online at the moment");
            return;
        }

        if (initiate.getClanMembership().getClanId() != clanMembership.getClanId()) {
            mob.out("They are in another clan");
            return;
        }

        initiate.setClanMembership(null);
        Clans.getClan(clanMembership.getClanId()).getMembers().remove(mob.getPlayer().getName());
    }

}
