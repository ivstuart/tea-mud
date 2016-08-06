package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;

public class Debug extends AdminCommand {

	@Override
	public void execute(Mob mob_, String input_) {

		super.execute(mob_,input_);
		
		if (input_.length() > 0) {
			Mob mob = mob_.getRoom().getMob(input_);
			if (mob != null) {
				mob_.out("Mob Info");
				mob_.out(mob.toString());
			}
			return;
		}
	}
}
