/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.World;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AllWorldStats extends AdminCommand {

	@Override
	public void execute(Mob mob, String input) {
		
		super.execute(mob,input);

		int numberOfMobs = World.getMobs().size();

		mob.out("Number of mobs    :"+numberOfMobs);

		int numberOfRooms = World.getRooms().size();

		mob.out("Number of rooms   :"+numberOfRooms);

		int numberOfPlayers = World.getPlayers().size();

		mob.out("Number of players :"+numberOfPlayers);

	}

}
