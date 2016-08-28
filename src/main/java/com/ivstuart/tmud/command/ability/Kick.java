/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.fighting.action.FightAction;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.MobStatus;

/**
 * @author Ivan Stuart
 */
public class Kick extends BaseCommand {

	private boolean checkMobStatus(Mob mob, Mob target) {

		if (!mob.getState().canMove()) {
			// You must be able to move to kick someone
			mob.out("You must be standing or flying to kick someone");
			return true;
		}

		MobStatus status = mob.getMobStatus();

		if (status.isGroundFighting()) {
			mob.out("You are ground fighting so can not kick someone");
			return true;
		}

		if (status.isImmobile()) {
			mob.out("You are immobile so can not kick someone");
			return true;
		}

		if (status.isOffBalance()) {
			mob.out("You are not balanced enough to kick someone");
			return true;
		}

		return false;
	}

	private boolean checkStatus(Mob mob, Mob target) {
		return checkMobStatus(mob, target);
	}

	// TODO vis hiden checks?
	@Override
	public void execute(Mob mob, String input) {

		if (!mob.getLearned().hasLearned("kick")) {
			mob.out("You have no knowledge of kick");
			return;
		}

		if (mob.getRoom().isPeaceful()) {
			mob.out("You can not be aggressive in this room");
			return;
		}


		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to kick!");
			return;
		}

		if (checkStatus(mob, target)) {
			return;
		}

		mob.getFight().add(new FightActionKick(mob, target));

	}

	private void setKicked(Mob mob, Mob target) {
		mob.getMobStatus().setOffBalance(2);

		int level = mob.getPlayer().getData().getLevel();

		DamageManager.deal(mob, target, level * DiceRoll.ONE_D_SIX.roll());

	}

	class FightActionKick extends FightAction {

		public FightActionKick(Mob me, Mob target) {
			super(me, target);
		}

		@Override
		public void begin() {
			super.begin();
			durationMillis(500);
			out("<S-You prepare your/NAME prepares GEN-him>self to kick <T-you/NAME>.");

			if (checkMobStatus(getSelf(), getTarget())) {
				this.finished();
			}

		}

		@Override
		public void changed() {
			// TODO Auto-generated method stub

		}

		@Override
		public void ended() {
			// TODO Auto-generated method stub

		}

		@Override
		public void happen() {
			if (checkMobStatus(getSelf(), getTarget())) {
				this.finished();
			}

			// Success or fail
			Ability kickAbility = getSelf().getLearned().getAbility("kick");
			if (kickAbility.isSuccessful()) {
				out("<S-You/NAME> successfully kicked <T-you/NAME>.");
				setKicked(getSelf(), getTarget());
				if (kickAbility.isImproved()) {
					out("[[[[ Your ability to " + kickAbility.getId()
							+ " has improved ]]]]");
					kickAbility.improve();
				}
			} else {
				out("<S-You/NAME> miss<S-/es> kicking <T-you/NAME>.");
			}

			durationMillis(1500);
		}

	}

}
