/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.fighting.action.FightAction;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.MobStatus;

/**
 * @author Ivan Stuart
 */
public class Bash implements Command {

	class FightActionBash extends FightAction {

		public FightActionBash(Mob me, Mob target) {
			super(me, target);
		}

		@Override
		public void begin() {
			super.begin();

			// TODO review and test this
			if (!getSelf().getMv().deduct(10)) {
				out("You do not have enough movement left to bash");
				this.finished();
			}

			durationMillis(500);
			out("<S-You prepare your/NAME prepares GEN-him>self to bash <T-you/NAME>.");

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
			Ability bashAbility = getSelf().getLearned().getAbility("bash");
			if (bashAbility.isSuccessful()) {
				out("<S-You/NAME> successfully bashed <T-you/NAME>.");
				setBashed(getSelf(), getTarget());
				if (bashAbility.isImproved()) {
					out("[[[[ Your ability to " + bashAbility.getId()
							+ " has improved ]]]]");
					bashAbility.improve();
				}
			} else {
				out("<S-You/NAME> miss<S-/es> bashing <T-you/NAME>.");
			}

			durationMillis(2500);
		}

		@Override
		public boolean isMeleeEnabled() {
			return false;
		}

	}

	private boolean checkMobStatus(Mob self, Mob target) {

		AbilityHelper.canUseAbility(self,target,"bash");

		if (self.getMobStatus().isBashLagged()) {
			self.out("You are not ready to bash someone yet");
			return true;
		}

		return false;
	}

	private boolean checkStatus(Mob mob, Mob target) {
		return checkMobStatus(mob, target) || checkTargetStatus(mob, target);
	}

	private boolean checkTargetStatus(Mob mob, Mob target) {
		MobStatus targetStatus = target.getMobStatus();

		if (targetStatus.isBashed()) {
			mob.out(target.getName() + " is already bash!");
			return true;
		}

		if (targetStatus.isGroundFighting()) {
			mob.out(target.getName()
					+ " is ground fighting so can not be bashed!");
			return true;
		}

		if (targetStatus.isBashAlert()) {
			// TODO decide if movement should be taken for this - I think yes.
			mob.getMv().deduct(4);
			mob.out(target.getName() + " easily avoids your bash!");
			return true;
		}

		// Mob is base aware now for 8 seconds which follow.
		targetStatus.setBashAlert(8);

		return false;
	}

	@Override
	public void execute(Mob mob, String input) {

		if (!mob.getLearned().hasLearned("bash")) {
			mob.out("You have no knowledge of bash");
			return;
		}

		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to bash!");
			return;
		}

		if (checkStatus(mob, target)) {
			return;
		}

		mob.getFight().add(new FightActionBash(mob, target));

	}

	private void setBashed(Mob mob, Mob target) {
		mob.getMobStatus().setBashLagged(2);

		target.getMobStatus().setBashed(5);
		target.getMobStatus().setBashAlert(10);
		target.getMobStatus().setOffBalance(8);

	}

}
