/*
 * Created on 22-Sep-2003
 *
 */
package com.ivstuart.tmud.command.communication;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.World;

/**
 * @author stuarti
 * 
 */
public class Reply implements Command {

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {

		String name = mob.getLastToldBy().getName();

		if (name == null) {
			mob.out("No one has told you anything yet!");
			return;
		}

		Mob playerMob = World.getMob(name);

		if (!playerMob.isPlayer()) {
			mob.out("Talking with someone who is not a player!");
		}

		String message = "$D" + mob.getId() + " tells you -->" + input + "$J";
		playerMob.out(message);
	}

}
