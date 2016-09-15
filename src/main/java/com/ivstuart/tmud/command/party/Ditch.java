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
public class Ditch extends BaseCommand {

	/**
	 * 
	 */
	public Ditch() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {

		Mob toDitch = mob.getRoom().getMobs().get(input);

		if (!toDitch.isFollowing(mob)) {
			mob.out("They are not following you so you can not ditch them");
			return;
		}

		toDitch.setFollowing(null);

		mob.out("You stop "+toDitch.getName()+" from following you");

	}

}
