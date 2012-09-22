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
import com.ivstuart.tmud.fighting.CombatCal;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttackFlurry extends FightAction {

	/**
	 * @param character
	 */
	public AttackFlurry(Mob me, Mob target) {
		super(me, target);

		// Test output string because at construction the fight action might be
		// queued for action at a later time.
		out(new Msg(me, target,
				"<S-You ready your/NAME readies GEN-him>self to attack <T-you/NAME>."));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fighting.FightAction#begin()
	 */
	@Override
	public void begin() {
		super.begin();

		// When a queued command resolves it need to work out if the target is
		// still visble.

		duration(10);

		out(new Msg(getSelf(), getTarget(),
				"<S-You prepare your/NAME prepares GEN-him>self to attack <T-you/NAME>."));

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
		duration(1);
	}

	/*
	 * (non-Javadoc) Percentage Hit chance = ( attack / (attack + defence)) with
	 * a cap of 5% and 95% for fumble / critical hits.
	 */
	@Override
	public void happen() {

		if (!getSelf().getMv().deduct(25)) {
			out("You do not have enough movement available to flurry");
			this.finished();
		}

		// out("Debug:Target ="+target.getName());

		// if (getFighter().getRoom() != getTarget().getRoom())
		if (getSelf().getRoom().getMobs().contains(getTarget()) == false) {
			out(getTarget().getId() + " is no longer here to attack!");
			finished();
			return;
		}

		out("You open up with a flurry of attacks");

		// if (isSuccess(getFighter().getFight().getHitChance())) {
		if (isSuccess()) {
			hit();
		} else {
			miss();
		}

		if (isSuccess()) {
			hit();
		} else {
			miss();
		}
		Prompt.show(getSelf());

		duration(3);
	}

	private void hit() {

		// out("You hit someone called " + character.getName());

		// Dodging?

		// BasicDamage damage = new BasicDamage();

		// Weapon roll
		// damage.setRoll(1,400,0);
		getSelf().getWeapon();

		// damage.setRoll(1, 6, 0);

		// APB - default 3 multiplier

		// Stat based damage modifier.

		// Fumbles. Criticals.

		// Hit location

		// Take off armour

		// Saves

		// Could just send this object the FightAction to damage.
		DamageManager.deal(getSelf(), getTarget(), DiceRoll.ONE_D_SIX.roll());

	}

	@Override
	public boolean isMeleeEnabled() {
		return false;
	}

	/*
	 * TODO this must be updated to be based on the unarmed skill level skill
	 * punch, unarmed , unarmed I, II, III, IV, V etc...
	 * 
	 * skill * attack / (attack+defence)
	 */
	private boolean isSuccess() {
		int attack = CombatCal.getAttack(getSelf());

		int defence = CombatCal.getDefence(getSelf());

		int divisor = attack + defence;

		if (divisor < 5) {
			divisor = 5;
		}

		int chance = this.clipRange((attack * 100) / (divisor));

		/**
		 * System.out.println("Attack = "+attack); System.out.println("Defence =
		 * "+defence); System.out.println("Hit chance = "+chance); /**
		 */

		if ((Math.random() * 100) < chance) {
			return true;
		}
		return false;
	}

	/*
	 * Some attacks which fail can have even more possiblities... i.e drop
	 * weapon, hitting a friend etc... more fun.
	 */
	private void miss() {
		out(new Msg(getSelf(), getTarget(),
				"<S-You/NAME> miss<S-/es> attacking <T-you/NAME>."));

		// out("You miss punching " + getTarget().getName());
	}

}
