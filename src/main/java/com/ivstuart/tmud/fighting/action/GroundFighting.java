/*
 * Created on 24-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.fighting.action;

import com.ivstuart.tmud.command.info.Prompt;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.fighting.BasicDamage;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti TODO Always hit
 * 
 *         A traveling merchant knees you in the solar plexus!! A traveling
 *         merchant looks dazed and confused from lack of oxygen! A traveling
 *         merchant claws at you, trying to get a better position. A traveling
 *         merchant tries to elbow you, but you keep squeezing his neck, and he
 *         can't manage to get his elbow to your face.
 * 
 *         You bite into a traveling merchant's groin, ouch thats going to hurt!
 *         You gouge a traveling merchant's eyes! You apply an arm-bar to a
 *         traveling merchant. You apply a leg lock to a traveling merchant. You
 *         drive your knee into a traveling merchant's groin! You successfully
 *         put a air-choke on a traveling merchant! You drive your elbow into a
 *         traveling merchants solar plexus!! You strike into a traveling
 *         merchant's pressure point, numbing his leg. You continue strangling a
 *         traveling merchant! You start hyper-extending a traveling merchant
 *         limb! You hear a cracking noise as you hyper-extend a traveling
 *         merchant limb! You ram your forehead into a traveling merchant's
 *         face! You smash your elbow into the side of a traveling merchant's
 *         head! Blood runs everywhere as you rip a traveling merchant's neck
 *         with your teeth!
 * 
 *         [JOINTLOCK ] You WILL attempt wrist-bends and arm hyperextensions. [
 *         CHOKE ] You WILL attempt to choke your victim. [ BITE ] You WILL
 *         attempt to bite your victim. [ ELBOW ] You WILL use your elbows. [
 *         KNEE ] You WILL use your knees. [ HEADBUTT ] You WILL headbutt.
 */
public class GroundFighting extends FightAction {

	// TODO
	private int position; // -5 0 5 i.e. who is in a better position.

	/**
	 * @param character
	 */
	public GroundFighting(Mob me_, Mob target_) {
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
				"<S-You prepare your/NAME prepares GEN-him>self to bite <T-you/NAME>."));

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

		out(new Msg(getSelf(), getTarget(),
				"<S-You are/NAME is> ground fighting <T-you/NAME>."));

		if (getSelf().getRoom().getMobs().contains(getTarget()) == false) {
			out(getTarget().getId() + " is no longer here to attack!");
			finished();
			return;
		}

		hit();

		Prompt.show(getSelf());

		durationMillis(2100);
	}

	private void hit() {

		// out("You hit someone called " + character.getName());

		// Dodging?

		BasicDamage damage = new BasicDamage();

		// Weapon roll
		// damage.setRoll(1,400,0);
		getSelf().getWeapon();

		damage.setRoll(1, 6, 0);

		// APB - default 3 multiplier

		// Stat based damage modifier.

		// Fumbles. Criticals.

		// Hit location

		// Take off armour

		// Saves

		// Could just send this object the FightAction to damage.
		DamageManager.deal(getSelf(), getTarget(), damage.roll());

	}

	@Override
	public boolean isGroundFighting() {
		return true;
	}

}
