/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Corpse;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Investigate extends BaseCommand {

	/*
	 * 2ndary action?
	 */
	@Override
	public void execute(Mob mob, String input) {

		if (!mob.getLearned().hasLearned("investigate")) {
			mob.out("You have no knowledge of Investigate");
			return;
		}

		Item corpseItem = mob.getRoom().getInventory().get(input);

		if (!corpseItem.isCorpse()) {
			mob.out(input + " is not investigatable");
			return;
		}
		
		Corpse corpse = (Corpse)corpseItem;
		
		// Success or fail
		Ability ability = mob.getLearned().getAbility("investigate");

		if (ability.isSuccessful()) {
			mob.out(new Msg(mob, ("<S-You/NAME> successfully investigated.")));
			
			mob.out(corpse.investigation());
	
			if (ability.isImproved()) {
				mob.out("[[[[ Your ability to " + ability.getId()
						+ " has improved ]]]]");
				ability.improve();
			}
		} else {
			mob.out(new Msg(mob, ("<S-You/NAME> failed to investigate.")));
		}
	}

}
