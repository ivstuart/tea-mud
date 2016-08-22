/*
 * Created on 17-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.misc;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.server.Playing;
import com.ivstuart.tmud.state.Mob;
import org.apache.log4j.Logger;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class NullCommand extends BaseCommand {

	private static final Logger LOGGER = Logger.getLogger(NullCommand.class);

	@Override
	public void execute(Mob mob, String input) {
		mob.out("?");
		LOGGER.debug("NullCommand with input :"+input);

	}

}
