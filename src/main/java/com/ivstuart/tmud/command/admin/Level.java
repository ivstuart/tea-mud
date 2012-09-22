package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;

public class Level implements Command {

	/** TODO refactor this as it not great ! */
	@Override
	public void execute(Mob mob_, String input_) {

		int numberOfLevels = 1;

		if (input_.length() > 0) {
			numberOfLevels = Integer.parseInt(input_);
		}

		while (numberOfLevels-- > 0) {

			int xp = mob_.getPlayer().getData().getToLevelXp();

			mob_.getPlayer().getData().addXp(xp);

			mob_.getPlayer().checkIfLeveled();
		}
	}
}
