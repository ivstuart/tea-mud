/*
 * Created on 24-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.fighting.action;

import com.ivstuart.tmud.command.ability.Tackle;
import com.ivstuart.tmud.command.info.Prompt;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.fighting.BasicDamage;
import com.ivstuart.tmud.fighting.CombatCal;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Weapon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.ivstuart.tmud.constants.SkillNames.DUAL_WIELD;
import static com.ivstuart.tmud.constants.SkillNames.THIRD_ATTACK;
import static com.ivstuart.tmud.constants.SkillNames.UNARMED_COMBAT;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BasicAttack extends FightAction {

	private static final Logger LOGGER = LogManager.getLogger();

	/**
	 * @param character
	 */
	public BasicAttack(Mob me_, Mob target_) {
		super(me_, target_);

		// Test output string because at construction the fight action might be
		// queued for action at a later time.
		// out(new Message(me_,
		// target_,"<S-You ready your/NAME readies GEN-him>self to attack <T-you/NAME>."));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fighting.FightAction#begin()
	 */
	@Override
	public void begin() {

		super.begin();

		// Set this to vary the speed of attacks i.e 1000 / speed
		durationMillis(100);

		// getFighter().getRoom().out(msg_)

		out(new Msg(getSelf(), getTarget(),
				"<S-You prepare your/NAME prepares GEN-him>self to attack <T-you/NAME>."));

		// chance of slipping and going to ground fighting ?
		if (DiceRoll.ONE_D100.rollLessThanOrEqualTo(1)) {
			out("You slip and end up going to the ground to fight");
			Tackle.setTackled(getSelf(), getTarget());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fighting.FightAction#changed()
	 */
	@Override
	public void changed() {
		out(new Msg(getSelf(), getTarget(),
				"<S-You are/NAME is> disrupted from attacking <T-you/NAME>."));
		// duration(3000);

	}

	private int clipRange(int percentage) {
		if (percentage < 5) {
			percentage = 5;
		}
		if (percentage > 95) {
			percentage = 95;
		}
		return percentage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fighting.FightAction#ended()
	 */
	@Override
	public void ended() {

		// out("You end from punching someone");
		durationMillis(100);
	}

	/*
	 * (non-Javadoc) Percentage Hit chance = ( attack / (attack + defence)) with
	 * a cap of 5% and 95% for fumble / critical hits.
	 */
	@Override
	public void happen() {

		// check if currently bashed.
		if (getSelf().getMobStatus().isBashed()) {
			LOGGER.debug("Can not deal any damage because I am bashed");
			return;
		}

		out(new Msg(getSelf(), getTarget(),
				"<S-You are/NAME is> attacking <T-you/NAME>."));

		// Log.info("I am "+getSelf().getId()+" and I am targetting "+getTarget().getId());

		if (getSelf().getRoom().getMobs().contains(getTarget()) == false) {
			out(getTarget().getId() + " is no longer here to attack!");
			getSelf().getFight().stopFighting();
			finished();
			return;
		}

		Ability dualWield = getSelf().getLearned().getAbility(DUAL_WIELD);

		Weapon secondaryWeapon = null;

		if (dualWield != null) {
			// Check has two weapons to hand.
			// Get 2nd weapon details.
			if (dualWield.isSuccessful()) {

				secondaryWeapon = getSelf().getSecondaryWeapon();

				if (secondaryWeapon != null && isSuccess()) {
					hit(secondaryWeapon);
				}
			}
			// call happen(weapon2)
		}

		// Enhanced damage and armour penetration
		Ability secondAttack = getSelf().getLearned().getAbility(
				"second attack");
		Ability thirdAttack = getSelf().getLearned().getAbility(THIRD_ATTACK);

		Weapon weapon = getSelf().getWeapon();

		if (secondAttack != null && secondAttack.isSuccessful() && isSuccess()) {
			LOGGER.info("I am [ " + getSelf().getId()
					+ " ] hit hitting my target");
			hit(weapon);

			if (secondaryWeapon != null && isSuccess()) {
				hit(secondaryWeapon);
			}

		}

		if (thirdAttack != null && thirdAttack.isSuccessful() && isSuccess()) {
			LOGGER.info("I am [ " + getSelf().getId()
					+ " ] hit hitting my target");
			hit(weapon);

			if (secondaryWeapon != null && isSuccess()) {
				hit(secondaryWeapon);
			}
		}

		if (isSuccess()) {
			LOGGER.info("I am [" + getSelf().getId()
					+ "] hit hitting my target");
			hit(weapon);
		} else {
			miss();
			LOGGER.info("I am [" + getSelf().getId()
					+ "] miss hitting my target");
		}
		Prompt.show(getSelf());
		durationMillis(2100);
	}

	private void hit(Weapon weapon) {

		if (this.getTarget() == null) {
			out("Target is null hence your hit now misses");
			return;
		}

		BasicDamage damage = new BasicDamage();

		if (weapon != null) {
			damage.setRoll(weapon.getDamage());
		}
		else {
			// unarmed
			damage.setRoll(getSelf().getDamage());

			Ability unarmed = getSelf().getLearned().getAbility(UNARMED_COMBAT);

			if (unarmed != null) {
				if (unarmed.isSuccessful()) {
					damage.setRoll(2, 8, 2);
				}
			}
		}

		if (damage.getRoll() == null) {
			damage.setRoll(DiceRoll.ONE_D_SIX);
		}

		Ability enhancedDamage = getSelf().getLearned().getAbility(
				"enhanced damage");

		if (enhancedDamage != null && enhancedDamage.isSuccessful()) {
			damage.setMultiplier(2);
		}

		Ability armourPeneration = getSelf().getLearned().getAbility(
				"armour penetration");

		int damageAmount = damage.roll();

		if (getSelf().isPlayer()) {
			LOGGER.debug("Applying APB bonus multiplier of "+ getSelf().getPlayer().getAPB()+" to damage");
			damageAmount *= getSelf().getPlayer().getAPB();
		}

		// Mobs can have multiple attacks.
		if (!getSelf().isPlayer()) {
			int attacks = getSelf().getAttacks();

			for (int i=1;i<attacks;i++) {
				DamageManager.deal(getSelf(), getTarget(), damage.roll());
			}
		}

		DamageManager.deal(getSelf(), getTarget(), damageAmount);

	}

	/*
	 * Consider adding other types of unarmed combat
	 * punch, unarmed , unarmed I, II, III, IV, V etc...
	 * 
	 * skill * attack / (attack+defence)
	 */
	private boolean isSuccess() {
		if (getTarget() == null) {
			LOGGER.debug("Target already been killed");
			return true;
		}

		int attack = CombatCal.getAttack(getSelf());

		int defence = CombatCal.getDefence(getTarget());

		int divisor = attack + defence;

		if (divisor < 5) {
			divisor = 5;
		}

		int chance = this.clipRange((attack * 100) / (divisor));

		LOGGER.debug("hit chance is [" + chance + "] attack is [" + attack + "] defence is [" + defence + "]");

		return outcomeOfChance(chance);

	}

	/*
	 * Some attacks which fail can have even more possibilities ... i.e drop
	 * weapon, hitting a friend etc... more fun.
	 */
	private void miss() {
		out(new Msg(getSelf(), getTarget(),
				"<S-You/NAME> miss<S-/es> attacking <T-you/NAME>."));

		// out("You miss punching " + getTarget().getName());
	}

	protected boolean outcomeOfChance(int chance) {
		return DiceRoll.ONE_D100.rollLessThanOrEqualTo(chance);

	}

}
