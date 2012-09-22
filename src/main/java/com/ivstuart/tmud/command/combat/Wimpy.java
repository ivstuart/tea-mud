package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;

public class Wimpy implements Command {

	@Override
	public void execute(Mob mob, String input) {
		mob.out("todo wimpy!");
	}

}