/*
 * Created on 17-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.misc;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.server.Readable;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Password extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		mob.out("Enter curent password:");

//		mob.getPlayer().getConnection().setState(new Readable() {
//			@Override
//			public void read(String line) {
//				// Check if password matches
//			}
//		});

		/**
		 * TODO // Save character first String password =
		 * getConnection().getLine();
		 *
		 * if (!password.matches(getCharacter().getStats().getMiscStats().
		 * getPassword())) { out("Wrong password!"); return; }
		 *
		 * String newPassword = getConnection().getLine();
		 *
		 * String confirmPassword = getConnection().getLine();
		 *
		 * if (!newPassword.matches(confirmPassword)) { out("Passwords do not
		 * match!"); return; }
		 *
		 * getCharacter().getStats().getMiscStats().setPassword(newPassword);
		 *
		 */

		mob.out("Password set.");

	}

}
