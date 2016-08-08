package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.state.Mob;

public class ClearAffects extends AdminCommand {

	@Override
	public void execute(Mob mob, String input) {

		super.execute(mob,input);

		Mob target = mob.getRoom().getMob(input);

		if (target == null && input.length() > 0) {
			mob.out(input + " is not here to clear affects on!");
			return;
		}
		else {
			mob = target;
		}

		mob.out("Clearing all affects on "+mob.getName());
		mob.getMobAffects().clear();
	}
}
