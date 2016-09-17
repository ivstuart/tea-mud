/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Smite extends AdminCommand {

	/**
	 * Instantely kill any mob
	 */
	@Override
	public void execute(Mob mob, String input) {

		super.execute(mob,input);

		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to smite!");
			return;
		}

		// Set mob hp to 0 and deal damage to them
		target.getHp().setValue(-1);
		DamageManager.checkForDefenderDeath(mob, target);

        mob.getRoom().out(new Msg(mob, target, "<S-You/NAME> smites <T-you/NAME> to a pile of ash, with a pure bolt of lightning!"));

	}

}
