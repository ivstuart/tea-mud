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

import java.util.List;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TellGroup extends BaseCommand {


	public TellGroup() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob_, String input) {

		List<Mob> group = mob_.getPlayer().getGroup();

		if (group == null) {
			mob_.out("You are not currently part of any xp group");
			return;
		}

		for (Mob mob : group) {

			if (mob.getPlayer().getConfig().getChannelData().is(ChannelData.GROUP)) {
				mob.out("<" + mob_.getName() + "> say's " + input);
			}
		}

	}
}
