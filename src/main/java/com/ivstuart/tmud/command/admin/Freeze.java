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

import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.World;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Freeze extends AdminCommand {

	/**
	 * Freeze input from player to their mob.
	 * Lasts until freeze is removed. 
	 * Allowed to quit.
	 */
	@Override
	public void execute(Mob mob, String input) {

		super.execute(mob,input);
		
		Player player = World.getPlayer(input);
		
		if (player == null) {
			mob.out("No player by the name of "+input+" is currently playing");
			mob.out("Use ban if you have their name instead");
			return;
		}
		
		player.getMob().getMobStatus().setFrozen(8000);
		
		mob.out("Your freeze player "+player.getName());
		player.out("You have been frozen by an admin, you can only quit");
	}

}
