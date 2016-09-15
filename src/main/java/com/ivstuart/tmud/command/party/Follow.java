/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.party;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Follow extends BaseCommand {

	/**
	 * 
	 */
	public Follow() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {

		Mob toFollow = mob.getRoom().getMobs().get(input.toLowerCase());

		if (toFollow == null) {
			mob.out("No one to follow of the name " + input);
			return;
		}

		if (input.equalsIgnoreCase("me")) {
			toFollow = mob;
		}

		if (mob == toFollow) {
			mob.out("No point in following self, but you stop following anyone.");
			mob.setFollowing(null);
			return;
		}

		mob.setFollowing(toFollow);

		mob.out("You start following "+toFollow.getName());

	}

}
