/*
 * Created on 24-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.fighting.action;

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

		// TODO base speed on speed of attacks!!!!!!
		durationMillis(100);

		// getFighter().getRoom().out(msg_)

		out(new Msg(getSelf(), getTarget(),
				"<S-You prepare your/NAME prepares GEN-him>self to attack <T-you/NAME>."));

		// chance of slipping and going to ground fighting ?

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
		// TODO Auto-generated method stub
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
			// TODO decided if we need to disengage at this point if no other attackers then can leave aggro on last attacker
			// if other attackers pick one at random to target.
			getSelf().getFight().stopFighting(); // TODO keep aggro on last attacker
			finished();
			return;
		}

		Ability dualWield = getSelf().getLearned().getAbility("dual wield");

		if (dualWield != null) {
			// Check has two weapons to hand.
			// Get 2nd weapon details.
			if (dualWield.isSuccessful()) {
				// TODO
			}
			// call happen(weapon2)
		}

		// Enhanced damage and armour penetration
		Ability secondAttack = getSelf().getLearned().getAbility(
				"second attack");
		Ability thirdAttack = getSelf().getLearned().getAbility("third attack");

		if (secondAttack != null && secondAttack.isSuccessful()) {
			LOGGER.info("I am [ " + getSelf().getId()
					+ " ] hit hitting my target");
			hit();
		}

		if (thirdAttack != null && thirdAttack.isSuccessful()) {
			LOGGER.info("I am [ " + getSelf().getId()
					+ " ] hit hitting my target");
			hit();
		}

		if (isSuccess()) {
			LOGGER.info("I am [" + getSelf().getId()
					+ "] hit hitting my target");
			hit();
		} else {
			miss();
			LOGGER.info("I am [" + getSelf().getId()
					+ "] miss hitting my target");
		}
		Prompt.show(getSelf());
		durationMillis(2100);
	}

	private void hit() {

		if (this.getTarget() == null) {
			out("Target is null hence your hit now misses");
			return;
		}

		BasicDamage damage = new BasicDamage();

		Weapon weapon = getSelf().getWeapon();

		if (weapon != null) {
			damage.setRoll(weapon.getDamage());
		}
		else {
			damage.setRoll(getSelf().getDamage());
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

		// APB - default 3 multiplier

		// Stat based damage modifier.

		// Fumbles. Criticals.

		// Hit location

		// Mobs can have multiple attacks.

		// LOGGER.debug(getSelf().getName()+" "+getTarget().getName()+" will deal "+damage.getRoll().toString());

		if (!getSelf().isPlayer()) {
			int attacks = getSelf().getAttacks();

			for (int i=1;i<attacks;i++) {
				DamageManager.deal(getSelf(), getTarget(), damage.roll());
			}
		}

		DamageManager.deal(getSelf(), getTarget(), damage.roll());

	}

	/*
	 * TODO this must be updated to be based on the unarmed skill level skill
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

		LOGGER.info("hit chance is ["+chance+"] attack is [" + attack + "] defence is [" + defence + "]");

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
