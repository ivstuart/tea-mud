package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.constants.AbilityConstants;
import com.ivstuart.tmud.state.Mob;

/**
 * @See DamageManager
 */
public class ShieldBlock implements Command {

	@Override
	public void execute(Mob mob, String input) {

		mob.out("Shield block is a passive ability, you just need to wield a sheild");

	}

}