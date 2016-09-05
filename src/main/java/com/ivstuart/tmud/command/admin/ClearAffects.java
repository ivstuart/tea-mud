package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.state.Attribute;
import com.ivstuart.tmud.state.Mob;

public class ClearAffects extends AdminCommand {

	@Override
	public void execute(Mob mob, String input) {

		super.execute(mob,input);

		Mob target = null;
		if (input != null && input.length() > 0) {
			target = mob.getRoom().getMob(input);

			if (target == null) {
				mob.out(input + " is not here to clear affects on!");
				return;
			}
			mob = target;
		}

		mob.out("Clearing all affects on "+mob.getName());

		Attribute drunk = mob.getPlayer().getData().getDrunkAttribute();
		Attribute poison = mob.getPlayer().getData().getPoisonAttribute();

		drunk.setValue(0);
		poison.setValue(0);

		mob.getPlayer().getData().getHunger().setValue(500);
		mob.getPlayer().getData().getThirst().setValue(500);

		// Best to remove effects first
		mob.getMobAffects().removeAll();

		mob.getMobAffects().clear();
	}
}
