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

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.state.Mob;

public class Level extends AdminCommand {

	@Override
	public void execute(Mob mob_, String input_) {

		super.execute(mob_,input_);
		
		int numberOfLevels = 1;

		if (input_.length() > 0) {
			numberOfLevels = Integer.parseInt(input_);
		}

		while (numberOfLevels-- > 0) {

			long xp = mob_.getPlayer().getData().getToLevelXp();

			mob_.getPlayer().getData().addXp(xp);

			mob_.getPlayer().checkIfLeveled();
		}
	}
}
