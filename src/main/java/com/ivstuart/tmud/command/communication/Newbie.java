/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.communication;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.Channel;
import com.ivstuart.tmud.world.ChannelHistory;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Newbie implements Command {

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {
		Channel c = ChannelHistory.getInstance().getNewbie();

		if (input.length() > 0) {

			c.add("$K(" + mob.getId() + ") " + input + "$J");
		} else {
			mob.out("$H>>>>>>>>>>>>>>> Newbie History <<<<<<<<<<<<<<<$J");
			mob.out(c.toString(0));
		}
	}

}
