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
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.fighting.action.FightAction;
import com.ivstuart.tmud.fighting.action.GroundFighting;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.MobStatus;

import static com.ivstuart.tmud.constants.SkillNames.TACKLE;

/**
 * @author Ivan Stuart
 */
public class Tackle extends BaseCommand {


	public static void setTackled(Mob mob, Mob target) {
		mob.getFight().setMelee(new GroundFighting(mob, target));
		mob.getMobStatus().setGroundFighting(10);
		target.getFight().setMelee(new GroundFighting(target, mob));
		target.getMobStatus().setGroundFighting(10);

	}

	private boolean checkStatus(Mob mob, Mob target) {
		return isMobUnableTo(mob, target) || checkTargetStatus(mob, target);
	}

	private boolean checkTargetStatus(Mob mob, Mob target) {

		if (target.isImmunityTackle()) {
			mob.out(target.getName() + " is immune to being tackled!");
			return true;
		}

		MobStatus targetStatus = target.getMobStatus();

		if (targetStatus.isGroundFighting()) {
			mob.out(target.getName() + " is ground fighting already!");
			return true;
		}

		return false;
	}

	@Override
	public void execute(Mob mob, String input) {

		if (!mob.getLearned().hasLearned(TACKLE)) {
			mob.out("You have no knowledge of " + TACKLE);
			return;
		}

		if (mob.getRoom().isPeaceful()) {
			mob.out("You can not be aggressive in this room");
			return;
		}

		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to " + TACKLE + "!");
			return;
		}

		if (checkStatus(mob, target)) {
			return;
		}

		mob.getFight().add(new FightActionTackle(mob, target));

	}

	private boolean isMobUnableTo(Mob mob, Mob target) {

		if (!mob.getState().canMove()) {
			// You must be able to move to bash someone
			mob.out("You must be standing or flying to tackle someone");
			return true;
		}

		if (null != mob.getWeapon()) {
			mob.out("You must have your hands free to tackle someone");
			return true;
		}

		MobStatus status = mob.getMobStatus();

		if (status.isGroundFighting()) {
			mob.out("You are ground fighting already so can not tackle someone");
			return true;
		}

		if (status.isImmobile()) {
			mob.out("You are immobile so can not tackle someone");
			return true;
		}

		if (status.isBashed()) {
			mob.out("You are bashed so can not tackle someone");
			return true;
		}

		if (status.isBashLagged()) {
			mob.out("You are not ready to tackle someone yet");
			return true;
		}

		return false;
	}

	class FightActionTackle extends FightAction {

		public FightActionTackle(Mob me, Mob target) {
			super(me, target);
		}

		@Override
		public void begin() {
			super.begin();

			if (!getSelf().getMv().deduct(10)) {
				out("You do not have enough movement left to tackle");
				this.finished();
			}

			durationMillis(500);
			out(new Msg(getSelf(), getTarget(), "<S-You prepare your/NAME prepares GEN-him>self to tackle <T-you/NAME>."));

			if (isMobUnableTo(getSelf(), getTarget())) {
				this.finished();
			}

		}

		@Override
		public void changed() {


        }

		@Override
		public void ended() {


        }

		@Override
		public void happen() {

			if (isMobUnableTo(getSelf(), getTarget())) {
				this.finished();
			}

			if (getTarget().getFight().isGroundFighting()) {
				getSelf().out("Your target is already ground fighting, you have no clean line of attack");
				return;
			}

			Fight.startCombat(getSelf(), getTarget());

			// Success or fail
            Ability ability = getSelf().getLearned().getAbility(TACKLE);

			// Always successful against a sleeping opponent
			if (!ability.isNull() && ability.isSuccessful() && DiceRoll.ONE_D100.rollMoreThan(50) || getTarget().getState().isSleeping()) {
				out(new Msg(getSelf(), getTarget(), "<S-You/NAME> successfully tackled <T-you/NAME> to the ground."));
				setTackled(getSelf(), getTarget());
				if (ability.isImproved()) {
					out("[[[[ Your ability to " + ability.getId()
							+ " has improved ]]]]");
					ability.improve();
				}
			} else {
				out(new Msg(getSelf(), getTarget(), "<S-You/NAME> miss<S-/es> tackling <T-you/NAME>."));
			}

			durationMillis(2500);
		}

		@Override
		public boolean isMeleeEnabled() {
			return false;
		}

	}

}
