/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.clan;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.ClanMembership;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.World;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ClanDemote extends BaseCommand {

    /**
     *
     */
    public ClanDemote() {
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

        Player initiate = World.getPlayer(input);

        if (initiate == null) {
            mob.out("There is no player by the name " + input + " online at the moment");
            return;
        }

        if (initiate.getClanMembership().getClanId() != clanMembership.getClanId()) {
            mob.out("They are in another clan");
            return;
        }

        if (clanMembership.getLevel() <= initiate.getClanMembership().getLevel()) {
            mob.out("You need to be more senior in your clan to promote this person");
            return;
        }

        mob.out("You demote player " + input + " a level in the clan");
        initiate.getClanMembership().demote();


    }

}
