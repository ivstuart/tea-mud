/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.Equipable;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.fighting.action.FightAction;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Weapon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Ivan Stuart
 */
public class BackStab extends BaseCommand {

	private static final Logger LOGGER = LogManager.getLogger();

	private boolean checkMobStatus(Mob self, Mob target) {

		return AbilityHelper.canUseAbility(self, target, "backstab");

	}

	private boolean isTargetFighting(Mob mob, Mob target) {

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

		if (mob.getRoom().isPeaceful()) {
			mob.out("You can not be aggressive in this room");
			return;
		}

		// check has a piercing weapon equiped.
		Equipable weaponEq = mob.getEquipment().getPrimary();

		if (weaponEq instanceof Weapon) {
			Weapon weapon = (Weapon) weaponEq;
			if (weapon.getSkill().indexOf("piercing") == -1) {
				mob.out("Your primary weapon must be piercing in order to backstab");
				return;
			}
		} else {
			mob.out("No primary weapon equiped which is piercing in order to backstab");
			return;
		}

		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to backstab!");
			return;
		}

		if (!checkMobStatus(mob, target)) {
			mob.out("Not in the right position to backstab mob");
			return;
		}

		if (isTargetFighting(mob, target)) {
			mob.out("Is already fighting so use circle instead");
			return;
		}

		if (target.isAware()) {
			mob.out("This mob is very alert and will not let you get behind them");
			return;
		}

		LOGGER.debug("You start to backstab");
		mob.getFight().add(new FightActionBackStab(mob, target));

	}

	class FightActionBackStab extends FightAction {

		public FightActionBackStab(Mob me, Mob target) {
			super(me, target);
		}

		@Override
		public void begin() {
			super.begin();

			if (!getSelf().getMv().deduct(20)) {
				out("You do not have enough movement left to backstab");
				this.finished();
			}

			durationMillis(500);
            out(new Msg(getSelf(), getTarget(), ("<S-You prepare your/NAME prepares GEN-him>self to backstab <T-you/NAME>.")));

			if (checkMobStatus(getSelf(), getTarget())) {
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
			if (checkMobStatus(getSelf(), getTarget())) {
				this.finished();
			}

			// Success or fail
			Ability bsAbility = getSelf().getLearned().getAbility("backstab");
			if (bsAbility.isSuccessful()) {
                out(new Msg(getSelf(), getTarget(), ("<S-You/NAME> successfully backstabed <T-you/NAME>.")));

				int dex = getSelf().getPlayer().getAttributes().getDEX().getValue();

				// source initial damage roll from weapon and modify this by dex and skill
				Weapon weapon = getSelf().getWeapon();

				if (weapon == null) {
					out("You can not backstab with no weapon");
					return;
				}

				DiceRoll damage = weapon.getDamage();

				// Could just send this object the FightAction to damage.
				DamageManager.deal(getSelf(), getTarget(), damage.roll()+dex);

				if (bsAbility.isImproved()) {
					out("[[[[ Your ability to " + bsAbility.getId()
							+ " has improved ]]]]");
					bsAbility.improve();
				}
			} else {
                out(new Msg(getSelf(), getTarget(), ("<S-You/NAME> miss<S-/es> backstab <T-you/NAME>.")));
            }

			durationMillis(2500);
		}

		@Override
		public boolean isMeleeEnabled() {
			return false;
		}

	}

}
