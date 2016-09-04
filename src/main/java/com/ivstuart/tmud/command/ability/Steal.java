/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.constants.SkillNames.STEAL;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Steal extends BaseCommand {

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

		if (target.isAware()) {
			mob.out(input + " is too alert to steal from!");
			return;
		}
		
		// rule decision if fighting alignment impact this option
		if (target.isPlayer() && (target.isGood() == mob.isGood())) {
			mob.out(input + " is a player, hence no stealing");
			return;
		}

        Ability steal = mob.getLearned().getAbility(STEAL);

        if (steal == null || steal.isNull()) {
            mob.out("You have no knowledge of steal");
			return;
		}


		if (steal.isSuccessful()) {
			mob.out(new Msg(mob, ("<S-You/NAME> successfully pilfer something...")));

			checkForStealingItems(mob, target);

			int amount = DiceRoll.ONE_D100.roll();
			
			int available = target.getInventory().getPurse().getValue();
			
			int taken = Math.min(amount, available);
			
			Money money = new Money(Money.COPPER,taken);

			// make some mobs aware of this and respond accordingly.
			target.getInventory().getPurse().remove(money);
			mob.getInventory().add(money);
			
			if (steal.isImproved()) {
				mob.out("[[[[ Your ability to " + steal.getId()
						+ " has improved ]]]]");
				steal.improve();
			}
		} else {
			// Decide if this is all mobs all the time or not.
			Fight.startCombat(mob, target);
		}


	}

	private boolean checkForStealingItems(Mob mob, Mob target) {
		if (DiceRoll.ONE_D100.rollLessThanOrEqualTo(5)) {
			return false;
		}

		Item item = target.getInventory().getItems().removeRandom();
		mob.getInventory().add(item);
		mob.out("You steal a " + item.getBrief() + " from " + mob.getName());

		return true;
	}

}
