/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Steal implements Command {

	/*
	 * 2ndary action?
	 */
	@Override
	public void execute(Mob mob, String input) {

		if (!mob.getLearned().hasLearned("steal")) {
			mob.out("You have no knowledge of steal");
			return;
		}

		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to steal from!");
			return;
		}
		
		// TODO optional make it possible to steal other players
		// rule decision if fighting alignment impact this option
		if (target.isPlayer()) {
			mob.out(input + " is a player, hence no stealing");
			return;
		}

		Ability steal = mob.getLearned().getAbility("steal");

		if (steal == null) {
			mob.out("You have no knowledge of steal");
			return;
		}


		if (steal.isSuccessful()) {
			mob.out("<S-You/NAME> successfully pilfer something...");

			// TODO optional make it possible to steal other inventory items.
			int amount = DiceRoll.ONE_D100.roll();
			
			int available = target.getInventory().getPurse().getValue();
			
			int taken = Math.min(amount, available);
			
			Money money = new Money(Money.COPPER,taken);
			
			// TODO make some mobs aware of this and respond accordingly.
			target.getInventory().getPurse().remove(money);
			mob.getInventory().add(money);
			
			if (steal.isImproved()) {
				mob.out("[[[[ Your ability to " + steal.getId()
						+ " has improved ]]]]");
				steal.improve();
			}
		}
		
		
	}

}
