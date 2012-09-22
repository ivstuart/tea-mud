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
import com.ivstuart.tmud.utils.*;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Tell implements Command {

	/**
	 * tell | whisper | ask <player> <string>
	 */
	public Tell() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob_, String input) {
		// TODO Auto-generated method stub
		StringPair inputPair = Parser.parseMessage(input);

		String message = "$D" + mob_.getId() + " tells you -->"
				+ inputPair.getTarget() + "$J";

		String name = inputPair.getSource();

		if (World.isOnline(name) == false) {
			mob_.out("Tell who?");
			return;
		}

		Mob playerMob = World.getMob(name);

		playerMob.out(message);
		mob_.out("You:" + message);

		playerMob.setLastToldBy(mob_);

	}
}
