/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AllRoomStats extends AdminCommand {

	@Override
	public void execute(Mob mob, String input) {
		
		super.execute(mob,input);
		
		Room target = mob.getRoom();

		if (target == null) {
			mob.out(input + " is not here to get all stats on!");
			return;
		}

		mob.out("ID = "+target.getId());

		mob.out(target.toString());

	}

}
