/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.party;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Follow implements Command {

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

		Mob toFollow = mob.getRoom().getMobs().get(input);

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
