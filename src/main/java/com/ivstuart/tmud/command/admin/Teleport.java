/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.World;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Teleport extends AdminCommand {

	/**
	 * Teleport instantely to new target location
	 */
	@Override
	public void execute(Mob mob, String input) {

		super.execute(mob,input);
		
		Room toRoom = World.getRoom(input);

		if (toRoom != null) {
			mob.out("You teleport to " + toRoom.getId());
			mob.getRoom().remove(mob);
			toRoom.add(mob);
		} else {
			mob.out("Room " + input + " not found!");
		}
	}

}
