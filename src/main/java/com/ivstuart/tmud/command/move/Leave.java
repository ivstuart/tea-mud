package com.ivstuart.tmud.command.move;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.person.movement.MoveManager;
import com.ivstuart.tmud.state.Mob;

public class Leave extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		// TODO
		MoveManager.random(mob);

	}

}