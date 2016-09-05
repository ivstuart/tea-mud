/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.constants.SkillNames.SEARCHING;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Search extends BaseCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {

        Ability ability = mob.getLearned().getAbility(SEARCHING);

        if (ability == null || ability.isNull()) {
            mob.out("You have no knowledge of how to search!");
			return;
		}

		if (!mob.getMv().deduct(10)) {
			mob.out("You do not have enough movement to search.");
			return;
		}

		if (!ability.isSuccessful()) {
			mob.out("Your search was unsucessful");
			return;
		}

		boolean foundSomething = false;

		foundSomething = foundSomething || searchRoomForHiddenMobs(mob, ability);

		foundSomething = foundSomething || searchRoomForHiddenItems(mob);

		foundSomething = foundSomething || searchRoomForHiddenExits(mob);

		if (!foundSomething) {
			mob.out("Nothing here hidden to find");
		}


	}

	public boolean searchRoomForHiddenExits(Mob mob) {
		for (Exit exit : mob.getRoom().getExits()) {
			if (exit.isHidden()) {
				mob.out("You found some additional way out!");
				searchExit(exit);
				return true;
			}
		}
		return false;
	}

	private void searchExit(Exit exit) {
		exit.setHidden(false);

	}

	public boolean searchRoomForHiddenItems(Mob mob) {

		for (Item item : mob.getRoom().getInventory().getItems()) {
			if (item.isHidden()) {
				mob.out("You found something!");
				searchItem(item);
				return true;
			}
		}
		return false;
	}

	private void searchItem(Item item) {
		item.setHidden(false);
	}

	public boolean searchRoomForHiddenMobs(Mob mob, Ability ability) {
		for (Mob roomMob : mob.getRoom().getMobs()) {

			if (roomMob == mob) {
				continue;
			}

			if (roomMob.isHidden()) {
				mob.out("You found someone!");

				if (ability.isImproved()) {
					mob.out("[[[[ Your ability to " + ability.getId()
							+ " has improved ]]]]");
					ability.improve();
				}

				searchMob(roomMob);
				return true;
			}
		}
		return false;
	}

	private void searchMob(Mob roomMob) {
		roomMob.setHidden(false);
		roomMob.out("You have been found and are no longer hidden");
		roomMob.getMobStatus().setHidden(0);

	}

}
