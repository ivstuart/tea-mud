/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.clan;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.ClanMembership;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.Clans;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ClanLeave extends BaseCommand {

    /**
     *
     */
    public ClanLeave() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob, String input) {

        if (!input.equals("yes")) {
            mob.out("You must type 'clanleave yes' to confirm leaving a clan");
            return;
        }

        ClanMembership clanMembership = mob.getPlayer().getClanMembership();

        if (clanMembership == null) {
            mob.out("You are not in a clan");
            return;
        }

        mob.out("You leave your clan, bye bye");
        mob.getPlayer().setClanMembership(null);
        Clans.getClan(clanMembership.getClanId()).getMembers().remove(mob.getPlayer().getName());
    }

}
