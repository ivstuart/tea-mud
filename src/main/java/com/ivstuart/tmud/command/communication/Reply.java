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
 * Created on 22-Sep-2003
 *
 */
package com.ivstuart.tmud.command.communication;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.World;

/**
 * @author stuarti
 * 
 */
public class Reply extends BaseCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {

		Mob lastToldByMob = mob.getLastToldBy();

		if (lastToldByMob == null) {
			mob.out("No one has told you anything yet!");
			return;
		}

		String name = lastToldByMob.getName();

		if (name == null) {
			mob.out("No one has told you anything yet!");
			return;
		}

		Mob playerMob = World.getMob(name.toLowerCase());

		if (playerMob == null) {
			mob.out("You can not reply to " + name + " they have left the mud world");
			return;
		}

		if (!playerMob.isPlayer()) {
			mob.out("Talking with someone who is not a player!");
		}

		String message = "$D" + mob.getId() + " tells you -->" + input + "$J";
		playerMob.out(message);
	}

}
