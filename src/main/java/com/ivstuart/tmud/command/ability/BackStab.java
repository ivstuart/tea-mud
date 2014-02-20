/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.fighting.action.FightAction;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;

/**
 * @author Ivan Stuart
 */
public class BackStab implements Command {

	class FightActionBackStab extends FightAction {

		public FightActionBackStab(Mob me, Mob target) {
			super(me, target);
		}

		@Override
		public void begin() {
			super.begin();

			// TODO review and test this
			if (!getSelf().getMv().deduct(20)) {
				out("You do not have enough movement left to backstab");
				this.finished();
			}

			durationMillis(500);
			out("<S-You prepare your/NAME prepares GEN-him>self to backstab <T-you/NAME>.");

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
			Ability bashAbility = getSelf().getLearned().getAbility("backstab");
			if (bashAbility.isSuccessful()) {
				out("<S-You/NAME> successfully backstabed <T-you/NAME>.");

				// TODO assign damage and if not fighting target each other
				int dex = getSelf().getPlayer().getAttributes().getDEX().getValue();
				
				DiceRoll damage = new DiceRoll(3,6,dex);
				
				// Could just send this object the FightAction to damage.
				DamageManager.deal(getSelf(), getTarget(), damage.roll());

				if (bashAbility.isImproved()) {
					out("[[[[ Your ability to " + bashAbility.getId()
							+ " has improved ]]]]");
					bashAbility.improve();
				}
			} else {
				out("<S-You/NAME> miss<S-/es> backstab <T-you/NAME>.");
			}

			durationMillis(2500);
		}

		@Override
		public boolean isMeleeEnabled() {
			return false;
		}

	}

	private boolean checkMobStatus(Mob self, Mob target) {

		AbilityHelper.canUseAbility(self, target, "backstab");

		return false;
	}

	private boolean checkStatus(Mob mob, Mob target) {
		return checkMobStatus(mob, target) || checkTargetStatus(mob, target);
	}

	private boolean checkTargetStatus(Mob mob, Mob target) {

		if (target.getFight().isFighting()) {
			mob.out(target.getName()
					+ " is fighting, you can not sneak up to backstab!");
			return true;
		}

		return false;
	}

	@Override
	public void execute(Mob mob, String input) {

		if (!mob.getLearned().hasLearned("backstab")) {
			mob.out("You have no knowledge of backstab");
			return;
		}

		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to backstab!");
			return;
		}

		if (checkStatus(mob, target)) {
			return;
		}

		mob.getFight().add(new FightActionBackStab(mob, target));

	}

}
