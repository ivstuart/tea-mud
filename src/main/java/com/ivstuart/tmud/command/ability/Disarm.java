/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.fighting.action.FightAction;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.MobStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.ivstuart.tmud.constants.SkillNames.DISARM;

/**
 * @author Ivan Stuart
 */
public class Disarm extends BaseCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    private boolean checkMobStatus(Mob mob, Mob target) {

        if (mob.getState().stuck()) {
            mob.out("You must be standing or flying to disarm someone");
            return true;
        }

        MobStatus status = mob.getMobStatus();

        if (status.isGroundFighting()) {
            mob.out("You are ground fighting so can not disarm someone");
            return true;
        }

        if (status.isImmobile()) {
            mob.out("You are immobile so can not disarm someone");
            return true;
        }

        if (status.isBashed()) {
            mob.out("You are bashed so can not disarm someone");
            return true;
        }

        return false;
    }

    private void disarm(Mob mob, Mob target) {
        Item weapon = target.getWeapon();

        if (weapon == null) {
            mob.out("Your target has no weapon to disarm!");
            return;
        }

        LOGGER.debug("Getting worn " + weapon.getWorn());

        target.getEquipment().remove(weapon);

        target.getRoom().add(weapon);

        LOGGER.debug("Removing weapon from " + target.getName() + " and adding it to the room");

    }

    @Override
    public void execute(Mob mob, String input) {

        if (!mob.getLearned().hasLearned(DISARM)) {
            mob.out("You have no knowledge of disarm");
            return;
        }

        Mob target;

        if (input.isEmpty() && mob.getFight().isFighting()) {
            target = mob.getFight().getTarget();
        } else {
            target = mob.getRoom().getMob(input);
        }

        if (target == null) {
            mob.out(input + " is not here to disarm!");
            return;
        }

        if (checkMobStatus(mob, target)) {
            return;
        }

        mob.getFight().add(new FightActionDisarm(mob, target));

    }

    class FightActionDisarm extends FightAction {

        public FightActionDisarm(Mob me, Mob target) {
            super(me, target);
        }

        @Override
        public void begin() {
            super.begin();

            if (!getSelf().getMv().deduct(1)) {
                out("You do not have enough movement left to disarm");
                this.finished();
            }

            durationMillis(1000);
            out(new Msg(getSelf(), getTarget(), ("<S-You prepare your/NAME prepares GEN-him>self to disarm <T-you/NAME>.")));

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
            Ability ability = getSelf().getLearned().getAbility(DISARM);
            if (ability.isSuccessful(getSelf())) {
                out(new Msg(getSelf(), getTarget(), ("<S-You/NAME> successfully disarmed <T-you/NAME>.")));
                disarm(getSelf(), getTarget());
            } else {
                out(new Msg(getSelf(), getTarget(), ("<S-You/NAME> miss<S-/es> disarming <T-you/NAME>.")));
            }

            durationMillis(1000);
        }

    }

}
