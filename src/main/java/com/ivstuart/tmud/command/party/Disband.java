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

import java.util.Iterator;
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

        if (input.length() > 0) {
            Iterator<Mob> mobIterator = mob_.getPlayer().getGroup().iterator();

            for (; mobIterator.hasNext(); ) {
                Mob mob = mobIterator.next();

                if (mob.getName().equals(input)) {
                    mobIterator.remove();
                    mob.getPlayer().setGroup(null);
                }
            }

        }


	}

}
