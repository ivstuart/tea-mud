/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.party;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;

import java.util.List;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Disband extends BaseCommand {

	/**
	 * 
	 */
	public Disband() {
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
			mob_.out("You have no group to disband");
		}

		if (input.equalsIgnoreCase("all")) {

			for (Mob mob : group) {
				mob.getPlayer().setGroup(null);
				mob.out("You are disbanded from your current group");
			}

			group.clear();
		}

		// TODO code to disband a single member of the group.

	}

}
