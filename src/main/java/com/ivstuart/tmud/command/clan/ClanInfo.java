/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.clan;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.ClanMembership;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.Clan;
import com.ivstuart.tmud.world.Clans;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ClanInfo extends BaseCommand {
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     *
     */
    public ClanInfo() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob, String input) {

        Clan clan = null;
        try {
            clan = Clans.getClan(input);
        } catch (NumberFormatException nfe) {
            LOGGER.warn("Issue getting clan", nfe);
        }

        if (clan == null) {
            mob.out("Try clan list to get the clan id number first");
            return;
        }

        mob.out("Clan name [" + clan.getName() + "] and leader [" + clan.getLeader() + "] number of members [" + clan.getMembers().size() + "]");

        ClanMembership clanMembership = mob.getPlayer().getClanMembership();

        if (clanMembership != null && clan.getClanId() == clanMembership.getClanId()) {
            if (clanMembership.getLevel() > 4) {
                mob.out("Applications for clan membership are:");
                mob.out("" + clan.getApplicants());
            }
        }



    }

}
