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
package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.person.statistics.affects.Affect;
import com.ivstuart.tmud.person.statistics.affects.DamageOverTime;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TestPoison extends AdminCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {

		super.execute(mob,input);

		Affect poisonAffect = new DamageOverTime(mob, "poison", 14,
				DiceRoll.ONE_D_SIX);

		mob.addAffect(poisonAffect);

	}

}
