/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.clan;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.Clan;
import com.ivstuart.tmud.world.Clans;

public class ClanList extends BaseCommand {

    /**
     *
     */
    public ClanList() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob, String input) {

        mob.out("Clan list:");

        int counter = 0;
        for (Clan clan : Clans.getClans()) {
            mob.out(counter + ". Clan name [" + clan.getName() + "] and leader " + clan.getLeader());
            counter++;
        }
    }

}
