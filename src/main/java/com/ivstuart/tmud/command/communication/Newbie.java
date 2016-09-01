/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.communication;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.config.ChannelData;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.World;
import com.ivstuart.tmud.world.Channel;
import com.ivstuart.tmud.world.ChannelHistory;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Newbie extends BaseCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {
		Channel c = ChannelHistory.getInstance().getNewbie();

		if (input.length() > 0) {

			String msg = "$K(" + mob.getId() + ") " + input + "$J";

			c.add(msg, mob.isGood());

			World.out(msg, mob.isGood(), ChannelData.NEWBIE);
		} else {
			mob.out("$H>>>>>>>>>>>>>>> Newbie History <<<<<<<<<<<<<<<$J");
			mob.out(c.toString(mob.isGood()));
		}
	}

}
