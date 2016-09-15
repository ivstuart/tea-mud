/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.misc;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.config.ConfigData;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Brief extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

        mob.out("Setting room decriptions to brief");
		mob.getPlayer().getConfig().getConfigData()
				.set(ConfigData.VERBOSE, false);
	}

}
