/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AdminCommand extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {
		
		if (!mob.isAdmin()) {
			mob.out("The command "+this.getClass().getSimpleName()+" is not available to you.");
			throw new AdminRuntimeException("Not a admin");
		}

	}

}
