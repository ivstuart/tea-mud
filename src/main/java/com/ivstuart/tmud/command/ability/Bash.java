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
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.fighting.action.FightAction;
import com.ivstuart.tmud.state.*;

import static com.ivstuart.tmud.constants.SkillNames.BASH;

/**
 * @author Ivan Stuart
 */
public class Bash extends BaseCommand {

    private boolean checkMobStatus(Mob self, Mob target) {

        AbilityHelper.canUseAbility(self, target, "bash");

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

        if (mob.getRoom().hasFlag(RoomEnum.PEACEFUL)) {
            mob.out("You can not be aggressive in this room");
            return;
        }

        Mob target;

        if (input.isEmpty() && mob.getFight().getTarget() != null) {
            target = mob.getFight().getTarget();
        } else {
            target = mob.getRoom().getMob(input);
        }

        if (target == null) {
            mob.out(input + " is not here to bash!");
            return;
        }

        if (target.hasMobEnum(MobEnum.BASH_IMMUNE)) {
            mob.out(input + " is too big to bash to the ground");
            return;
        }

        if (checkStatus(mob, target)) {
            return;
        }

        if (target.isInvisible() && !mob.hasDetectInvisible()) {
            mob.out(input + " is not seen here to bash!");
            return;
        }

        if (target.isHidden() && !mob.hasDetectHidden()) {
            mob.out(input + " is not seen here to bash!");
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

    class FightActionBash extends FightAction {

        public FightActionBash(Mob me, Mob target) {
            super(me, target);
        }

        @Override
        public void begin() {
            super.begin();

            if (!getSelf().getMv().deduct(10)) {
                out("You do not have enough movement left to bash");
                this.finished();
            }

            durationMillis(3000);
            out(new Msg(getSelf(), getTarget(), "<S-You prepare your/NAME prepares GEN-him>self to bash <T-you/NAME>."));

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

            Fight.startCombat(getSelf(), getTarget());

            if (checkMobStatus(getSelf(), getTarget())) {
                this.finished();
            }

            // Success or fail
            Ability bashAbility = getSelf().getLearned().getAbility(BASH);
            if (bashAbility.isSuccessful(getSelf())) {
                out(new Msg(getSelf(), getTarget(), "<S-You/NAME> successfully bashed <T-you/NAME>."));
                setBashed(getSelf(), getTarget());
            } else {
                out(new Msg(getSelf(), getTarget(), "<S-You/NAME> miss<S-/es> bashing <T-you/NAME>."));
            }

            durationMillis(2500);
        }

        @Override
        public boolean isMeleeEnabled() {
            return false;
        }

    }

}
