/*
 *  Copyright 2024. Ivan Stuart
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

package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.fighting.action.FightAction;
import com.ivstuart.tmud.state.mobs.Ability;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.mobs.MobStatus;
import com.ivstuart.tmud.state.places.RoomEnum;

import static com.ivstuart.tmud.constants.SkillNames.KICK;

/**
 * @author Ivan Stuart
 */
public class Kick extends BaseCommand {

    private boolean checkMobStatus(Mob mob, Mob target) {

        if (mob.getState().stuck()) {
            // You must be able to move to kick someone
            mob.out("You must be standing or flying to kick someone");
            return true;
        }

        MobStatus status = mob.getMobStatus();

        if (status.isGroundFighting()) {
            mob.out("You are ground fighting so can not kick someone");
            return true;
        }

        if (mob.getFight().isGroundFighting()) {
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

    @Override
    public void execute(Mob mob, String input) {

        if (!mob.getLearned().hasLearned("kick")) {
            mob.out("You have no knowledge of kick");
            return;
        }

        if (mob.getRoom().hasFlag(RoomEnum.PEACEFUL)) {
            mob.out("You can not be aggressive in this room");
            return;
        }

        Mob target;

        if (mob.getFight().isFighting() && input.isEmpty()) {
            target = mob.getFight().getTarget();
        } else {
            target = mob.getRoom().getMob(input);
        }


        if (target == null) {
            mob.out(input + " is not here to kick!");
            return;
        }

        if (checkStatus(mob, target)) {
            return;
        }

        if (target.isInvisible() && !mob.hasDetectInvisible()) {
            mob.out(input + " is not seen here to kick!");
            return;
        }

        if (target.isHidden() && !mob.hasDetectHidden()) {
            mob.out(input + " is not seen here to kick!");
            return;
        }

        mob.getFight().add(new FightActionKick(mob, target));

    }

    private void setKicked(Mob mob, Mob target) {
        mob.getMobStatus().setOffBalance(2);

        int level = mob.getPlayer().getData().getLevel();

        int kickBonus = mob.getEquipment().getKickBonus();

        DamageManager.deal(mob, target, level * (DiceRoll.ONE_D_SIX.roll() + kickBonus));

    }

    class FightActionKick extends FightAction {

        public FightActionKick(Mob me, Mob target) {
            super(me, target);
        }

        @Override
        public void begin() {
            super.begin();
            durationMillis(500);
            out(new Msg(getSelf(), getTarget(), "<S-You prepare your/NAME prepares GEN-him>self to kick <T-you/NAME>."));

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
            Ability kickAbility = getSelf().getLearned().getAbility(KICK);
            if (kickAbility.isSuccessful(getSelf())) {
                out(new Msg(getSelf(), getTarget(), "<S-You/NAME> successfully kicked <T-you/NAME>."));
                setKicked(getSelf(), getTarget());
            } else {
                out(new Msg(getSelf(), getTarget(), "<S-You/NAME> miss<S-/es> kicking <T-you/NAME>."));
            }

            getSelf().getFight().changeTarget(getTarget());

            durationMillis(1500);
        }

    }

}
