/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.World;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Users extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {


        String line = String.format(" %1$-3s %2$-5s %3$-16s %4$-16s %5$-4s %6$-8s %7$-16s",
                "Num",
                "Class",
                "Name",
                "State",
                "Idle",
                "Local Address",
                "Remote Address");
        mob.out(line);
        mob.out("$K~$J");

        int counter = 0;
        for (String playerName : World.getPlayers()) {
            counter++;
            Player player = World.getMob(playerName).getPlayer();
            // String line = playerName + player.getData().getTitle();
            line = String.format(" %1$-3s %2$-5s %3$-16s %4$-16s %5$-4s %6$-8s %7$-16s",
                    counter,
                    player.getProfession(),
                    player.getName(),
                    player.getMob().getState(),
                    player.getConnection().getIdle() / 1000,
                    player.getConnection().getLocalAddress(),
                    player.getConnection().getRemoteAddress());

			mob.out(line);
		}

        mob.out("$K~$J");
    }

}
