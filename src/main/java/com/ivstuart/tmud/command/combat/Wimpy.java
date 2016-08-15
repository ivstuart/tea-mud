package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;

public class Wimpy extends BaseCommand {

	/**
	 * Set a value or percentage of health to automatically trigger fleeing from
	 * combat
	 */
	@Override
	public void execute(Mob mob, String input) {

		if (input.length() == 0) {
			mob.out("Wimpy followed by the number of health points at which to flee");
		}

		try {
			int wimpy = Integer.parseInt(input);
			
			mob.out("Wimpy set to "+wimpy); 

			mob.setWimpy(wimpy);
		} catch (NumberFormatException e) {
			mob.out("Wimpy number formatting issue with input value "+input+", number only please");
		}
	}

}