package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.WorldTime;

public class Kill implements Command {

	@Override
	public void execute(Mob mob, String input) {
		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to kill!");
			return;
		}

		mob.getFight().getMelee().setTarget(target);

		target.getFight().getMelee().setTarget(mob);

		WorldTime.addFighting(mob);
		
		WorldTime.addFighting(target);

	}

}