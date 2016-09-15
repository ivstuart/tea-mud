/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.communication;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Attribute;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Say extends BaseCommand {

	// say | shout | holler <string>

	@Override
	public void execute(Mob mob, String input) {

		Attribute drunk = mob.getPlayer().getData().getDrunkAttribute();
		if (drunk.getValue() > 100) {
            mob.out("You are soo drunk you slur your words.");
            input = input.replaceAll(" ", "rr ");
        }

        mob.getRoom().out(mob.getId() + " says, \"" + input + "\"");
	}

}
