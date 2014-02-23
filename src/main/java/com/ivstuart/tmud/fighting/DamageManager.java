package com.ivstuart.tmud.fighting;

import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.constants.DamageConstants;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Corpse;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Prop;
import com.ivstuart.tmud.state.World;
import com.ivstuart.tmud.state.WorldTime;

public class DamageManager {

	public static void deal(Mob attacker, Mob defender, int damage) {


	
		// Check saves first
		damage = checkForDodge(defender, damage);
		
		damage = checkForShieldBlocking(defender, damage);
		
		// Take off armour at hit location
		//   if melee the armor at location 
		//      apply any armor peneration skills or specials to counter armour saves
		//   if spell damage the average armour minus any special elemental saves
		// TODO 

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

	private static int checkForDodge(Mob defender, int damage) {
		Ability dodge = defender.getLearned().getAbility("dodge");

		if (dodge != null && dodge.isSuccessful()) {
			defender.out("<S-You/NAME> successfully dodge missing most of the attack.");

			damage = (int) damage / 10;

			if (dodge.isImproved()) {
				defender.out("[[[[ Your ability to " + dodge.getId()
						+ " has improved ]]]]");
				dodge.improve();
			}
		}

		return damage;

	}

	public static int checkForShieldBlocking(Mob defender, int damage) {
		Ability shieldBlock = defender.getLearned().getAbility("shield block");

		if (shieldBlock != null) {
			if (defender.getEquipment().hasShieldEquiped()) {
				if (shieldBlock.isSuccessful()) {
					defender.out("<S-You/NAME> successfully shield block <T-you/NAME>.");

					damage -= 5;

					if (shieldBlock.isImproved()) {
						defender.out("[[[[ Your ability to "
								+ shieldBlock.getId() + " has improved ]]]]");
						shieldBlock.improve();
					}
				}
			}
		}
		return damage;
	}
}
