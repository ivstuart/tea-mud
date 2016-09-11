/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.clan;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.ClanMembership;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.Clan;
import com.ivstuart.tmud.world.Clans;
import com.ivstuart.tmud.world.World;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ClanAdd extends BaseCommand {

    /**
     *
     */
    public ClanAdd() {
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
            mob.out("You need to be more senior in your clan to add people");
            return;
        }

        Player initiate = World.getPlayer(input);

        if (initiate == null) {
            mob.out("There is no player by the name " + input + " online at the moment");
            return;
        }

        if (initiate == mob.getPlayer()) {
            mob.out("No need to add yourself your already in the clan");
            return;
        }

        if (initiate.getMob().isGood() != Clans.getClan(clanMembership.getClanId()).isAlignment()) {
            mob.out("You can not add them to the a clan, they have the wrong alignment!");
            return;
        }

        if (initiate.getClanMembership() != null) {
            mob.out("The person you want to add is already in clan ");
            return;
        }

        ClanMembership clanMembership1 = new ClanMembership();
        clanMembership1.setLevel(1);
        clanMembership1.setClanId(clanMembership.getClanId());

        initiate.setClanMembership(clanMembership1);
        mob.out("You add " + initiate.getName() + " to the clan");

        Clan clan = Clans.getClan(clanMembership.getClanId());

        initiate.out("You are added to the clan " + clan.getName());
        clan.addMembers(initiate.getName());

        if (clan.getApplicants() != null) {
            clan.getApplicants().remove(mob.getPlayer().getName());
        }
    }

}
