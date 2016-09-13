/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.fighting.action.FightAction;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.constants.SkillNames.CIRCLE;

/**
 * @author Ivan Stuart
 */
public class Circle extends BaseCommand {

	private boolean checkMobStatus(Mob self, Mob target) {

		AbilityHelper.canUseAbility(self, target, "circle");

		return false;
	}

	private boolean checkStatus(Mob mob, Mob target) {
		return checkMobStatus(mob, target) || checkTargetStatus(mob, target);
	}

	private boolean checkTargetStatus(Mob mob, Mob target) {

		if (!target.getFight().isFighting()) {
			mob.out(target.getName()
					+ " is not fighting, you can only circle during combat!");
			return true;
		}

		return false;
	}

	@Override
	public void execute(Mob mob, String input) {
		// Same as backstab but can use during combat only
		if (!mob.getLearned().hasLearned("circle")) {
			mob.out("You have no knowledge of circle");
			return;
		}

		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to circle!");
			return;
		}

		if (checkStatus(mob, target)) {
			return;
		}

		// Also need to check if another thief is circling at the same time
		// they will bump into each other and lose that circle in that event.


		mob.getFight().add(new FightActionCircle(mob, target));

	}

	class FightActionCircle extends FightAction {

		public FightActionCircle(Mob me, Mob target) {
			super(me, target);
		}

		@Override
		public void begin() {
			super.begin();

			getSelf().getMobStatus().setCircling(10000);

			if (!getSelf().getMv().deduct(15)) {
				out("You do not have enough movement left to circle");
				this.finished();
			}

			durationMillis(500);
			out(new Msg(getSelf(), getTarget(), ("<S-You prepare your/NAME prepares GEN-him>self to circle <T-you/NAME>.")));

			if (checkMobStatus(getSelf(), getTarget())) {
				this.finished();
			}

		}

		@Override
		public void changed() {
			getSelf().getMobStatus().setCircling(0);

		}

		@Override
		public void ended() {

		}

		@Override
		public void happen() {
			if (checkMobStatus(getSelf(), getTarget())) {
				this.finished();
			}

			// Check if another theif is attemping to also circle at the same time
			// Note only most progressed
			if (getTarget().getFight().isBeingCircled()) {
				getSelf().out(getTarget().getName()
						+ " is already being circled you collide and are off balance");
				durationMillis(5000);
				getSelf().getMobStatus().setCircling(2500); // Other theif gets caught out this way.
				getSelf().getMobStatus().setOffBalance(2500);
				return;
			}


			// Success or fail
            Ability ability = getSelf().getLearned().getAbility(CIRCLE);
            if (ability.isSuccessful(getSelf())) {
                out(new Msg(getSelf(), getTarget(), ("<S-You/NAME> successfully circled <T-you/NAME>.")));

                Fight.startCombat(getSelf(), getTarget(), true);

				int dex = getSelf().getPlayer().getAttributes().getDEX().getValue();

				DiceRoll damage = new DiceRoll(3,5,dex);

				// Could just send this object the FightAction to damage.
				DamageManager.deal(getSelf(), getTarget(), damage.roll());

			} else {
				out(new Msg(getSelf(), getTarget(), ("<S-You/NAME> miss<S-/es> circle <T-you/NAME>.")));
			}

			durationMillis(2500);
			getSelf().getMobStatus().setCircling(0);
		}

		@Override
		public boolean isMeleeEnabled() {
			return false;
		}

	}

}
