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
 * Created on 12-Nov-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.state;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.common.MobState.STAND;

public class Stand extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		if (mob.getMobStatus().isGroundFighting()) {
			mob.out("You are locked into ground fighting for the moment");
			return;
		} else if (mob.getFight().isGroundFighting()) {
			// chance to scramble up ?
			// effect if winning or losing gf if fails ?
			// use movement points
			if (mob.getMv() == null || !mob.getMv().deduct(5)) {
				mob.out("You do not have enough movement available to stand");
				return;
			}

			if (DiceRoll.ONE_D100.rollLessThan(50)) {
				mob.out("You try to scramble up to avoid fighting on the ground, but fail to get up");
				return;
			}

			mob.out("You try to scramble up to avoid fighting on the ground");
			mob.getFight().setMeleeToBasicAttack();
			mob.getTargetFight().setMeleeToBasicAttack();
			return;
		}

		if (mob.getState() == STAND) {
			mob.out("You are already standing!");
			return;
		}

		// Check allowed to change state
		if (mob.getRoom().isFlying()) {
			mob.out("You must continue to fly here");
			return;
		}

		// Change state and notify mob and room

		mob.out("You stop " + mob.getState().getDesc() + " and stand.");

		mob.setState(STAND);

	}

}
