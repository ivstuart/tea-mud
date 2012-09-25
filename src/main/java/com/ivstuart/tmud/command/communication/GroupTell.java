/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.communication;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.World;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GroupTell implements Command {

	/**
	 * 
	 */
	public GroupTell() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {

		String[] inputSplit = input.split(" ");

		if (inputSplit.length < 2) {
			mob.out("need source and target group to tell");
			return;
		}

		String message = "$D" + mob.getId() + " tells you -->" + inputSplit[1]
				+ "$J";

		String name = inputSplit[0];

		if (World.isOnline(name) == false) {
			mob.out("Tell who?");
			return;
		}

		Mob playerMob = World.getMob(name);

		playerMob.out(message);

		mob.out("You:" + message);

		playerMob.setLastToldBy(mob);

	}
}
