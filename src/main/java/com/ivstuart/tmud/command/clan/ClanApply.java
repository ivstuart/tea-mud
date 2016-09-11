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
public class ClanApply extends BaseCommand {

    /**
     *
     */
    public ClanApply() {
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

        if (clanMembership != null) {
            mob.out("You must have no clan in order to apply for one");
            return;
        }

        Clans.getClan(Integer.parseInt(input)).addApplicant(mob.getName());

        mob.out("You apply for the clan");

    }

}
