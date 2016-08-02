package com.ivstuart.tmud.fighting;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.constants.DamageConstants;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Armour;
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
		int armour = checkArmourAtHitLocation(defender);
		
		if (armour > 0) {
			// TODO no arm pen for spells
			armour = checkForArmourPenetration(attacker, armour);
		}
		
		damage = damage - armour;
		
		if (damage < 1) {
			attacker.out("Your feeble blow is deflected by their armour");
			return;
		}
		
		// TODO
		checkMagicalDamageSaves();

		Msg msg = new Msg(attacker, defender, DamageConstants.toString(damage));

		attacker.getRoom().out(msg);

		defender.getHp().decrease(damage);
		
		// TODO if not fighting or a heavy blow defender will target and attack attacker.

		checkForDefenderDeath(attacker, defender);

	}

	private static void checkMagicalDamageSaves() {
		// TODO Fire Water Earth Air and Magic saves to check
		// acts rather like armour
		
	}

	private static int checkArmourAtHitLocation(Mob defender) {
		// TODO FIXME
		Armour armour = defender.getEquipment().getTotalArmour();
		try {
			return armour.getRandomSlot();
		} catch (ArrayIndexOutOfBoundsException e) {
			return 0;
		}
	}

	private static int checkForArmourPenetration(Mob attacker, int armour) {
		if (armour < 1) {
			return armour;
		}
		
		// TODO armour penetration
		Ability penetration = attacker.getLearned().getAbility("armour penetration");

		if (penetration != null && penetration.isSuccessful()) {
			attacker.out("<S-You/NAME> skillfully hit between your opponents armour");

			armour -= DiceRoll.ONE_D_SIX.roll();

			if (penetration.isImproved()) {
				attacker.out("[[[[ Your ability to " + attacker.getId()
						+ " has improved ]]]]");
				penetration.improve();
			}
		}
		return Math.max(armour,0);
	}

	public static void checkForDefenderDeath(Mob attacker, Mob defender) {
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
