package com.ivstuart.tmud.fighting;

import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.constants.*;
import com.ivstuart.tmud.state.Corpse;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Prop;
import com.ivstuart.tmud.state.World;
import com.ivstuart.tmud.state.WorldTime;

public class DamageManager {

	public static void deal(Mob attacker, Mob defender, int damage) {

		// Check saves

		Msg msg = new Msg(attacker, defender, DamageConstants.toString(damage));

		attacker.getRoom().out(msg);

		defender.getHp().decrease(damage);

		// check for defender death!
		if (defender.getHp().getValue() <= 0) {
			defender.out("You have been killed!\n\n");

			defender.getFight().clear();
			// create corpse and move equipment and inventory to corpse
			Prop corpse = new Corpse(defender);

			// corpse.setShort("Corpse of "+defender.getName());
			corpse.setBrief("The corpse of a filthy " + defender.getName()
					+ " is lying here.");

			defender.getRoom().remove(defender);
			defender.getRoom().add(corpse);

			if (defender.isPlayer()) {
				World.getRoom("R-P2").add(defender);
				defender.getHp().setValue(1);
			} else {
				// Add mob to list of the dead ready for repopulation after a
				// timer.
				WorldTime.scheduleMobForRepopulation(defender);

				// Award xp to killing mob.
				int xp = defender.getXp();

				if (attacker.isPlayer()) {
					attacker.getPlayer().getData().addKill(defender);

					attacker.out("You gained [" + xp
							+ "] total experience for the kill.");

					attacker.getPlayer().checkIfLeveled();

					attacker.out("You hear a filthy rat's death cry. - TODO publish message to all in hearing range.");
				}

			}

			// clear any affects

			attacker.getFight().stopFighting();
			defender.getFight().stopFighting();
			//
		}

		// Check if is < 0 -> corpse

		/*
		 * // If you are attacked you automatically fight back unless you are
		 * already fighting if(this.isNotFighting()) { fighter.out("You
		 * retaliate against "+fighter.getName()); this.setTarget(fighter); //
		 * Characters need to configure a default response. this.start(new
		 * NoWeapon(fighter,fighter),true); } // TODO I don't like this
		 * if(attacker.getStats().getLifeStats().inflict(damage)) { // death
		 * fighter.getFight().addKill();
		 * attacker.getLocation().getRoom().add(new MudItem(attacker + "'s
		 * corpse")); attacker.getLocation().move(World.getPortal(true));
		 * attacker.out("You have been killed!");
		 * 
		 * fighter.out("You have killed "+attacker); // Last action as it would
		 * cause Null pointer issue with the above attacker.getFight().clear();
		 * 
		 * this.setTarget(null); }
		 */

	}
}
