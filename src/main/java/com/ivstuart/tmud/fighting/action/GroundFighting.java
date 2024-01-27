/*
 * Copyright 2024. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.ivstuart.tmud.fighting.action;

import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.command.info.Prompt;
import com.ivstuart.tmud.command.state.Stand;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.fighting.BasicDamage;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.person.config.FightData;
import com.ivstuart.tmud.person.config.FightEnum;
import com.ivstuart.tmud.state.Mob;

public class GroundFighting extends FightAction {

    private final String[] DESCRIPTION_START = {
            "<S-You/NAME> start hyper-extending a <T-you/NAME> limb!",
            "<S-You/NAME> successfully put a air-choke on a <T-you/NAME>!",
            "<S-You/NAME> bite into a <T-you/NAME> groin, ouch that's going to hurt!",
            "A <T-you/NAME> tries to elbow you, but you keep squeezing his neck, and he can't manage to get his elbow to your face.",
            "<S-You/NAME> start to swing you legs ready to knee",
            "<S-You/NAME> pull your head back and line up your forehead",
            "<S-You/NAME> bite into a <T-you/NAME> groin, ouch that's going to hurt!"};
    private final String[] DESCRIPTION_APPLY = {
            "<S-You/NAME> hear a cracking noise as you hyper-extend a <T-you/NAME> limb!",
            "A <T-you/NAME> looks dazed and confused from lack of oxygen!",
            "<S-You/NAME> bite into a <T-you/NAME> groin, ouch that's going to hurt!",
            "<S-You/NAME> drive your elbow into a <T-you/NAME> solar plexus!!",
            "<S-You/NAME> drive your knee into a <T-you/NAME> groin!",
            "<S-You/NAME> ram your forehead into a <T-you/NAME> face!",
            "Blood runs everywhere as you rip a <T-you/NAME> neck with your teeth!",
            "<S-You/NAME> gouge a <T-you/NAME> eyes!"};
    private final String[] DESCRIPTION_FAIL = {
            "<S-You/NAME> fail to get into the right position for a joint lock",
            "<S-You/NAME> fail to get into the right position to apply a choke hold",
            "<S-You/NAME> fail to get into the right position to bite",
            "<S-You/NAME> fail to get into the right position to elbow",
            "<S-You/NAME> fail to get into the right position to knee",
            "<S-You/NAME> fail to get into the right position for a headbutt",
            "<S-You/NAME> fail to get into the right position for a gouge"};
    private final String[] TARGET = {
            "temple",
            "nose",
            "eyes",
            "mastoids",
            "philtrum",
            "jaw",
            "carotid arteries",
            "throat",
            "floating ribs",
            "solar plexus",
            "groin",
            "thigh",
            "knee",
            "shin",
            "foot",
            "back of skull",
            "spine",
            "kidneys",
            "small of back",
            "coccyx",
            "back of knee",
            "achilles"};
    private final int attackType;
    private final boolean hasAChokeHold = false;
    private int position; // i.e. who is in a better position.

    /**
     * @param me_
     * @param target_
     */
    public GroundFighting(Mob me_, Mob target_) {
        super(me_, target_);

        if (me_.isPlayer()) {
            attackType = me_.getPlayer().getConfig().getFightData().getRandomAttackType().ordinal();
        } else {
            attackType = DiceRoll.ONE_D_SIX.roll() - 1;
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see fighting.FightAction#begin()
     */
    @Override
    public void begin() {

        super.begin();

        if (getSelf().getWimpy() > getSelf().getHp().getValue()) {
            getSelf().getFight().add(new com.ivstuart.tmud.fighting.action.Flee(getSelf()));
            out("You have reached your wimpy and will try to flee");
            return;
        }

        durationMillis(100);

        position += DiceRoll.ONE_D_SIX.roll() - 3;

        if (position < -20) {
            position = -20;
        } else if (position > 20) {
            position = 20;
        }

        out(new Msg(getSelf(), getTarget(), DESCRIPTION_START[attackType]));

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

    }

    /*
     * (non-Javadoc)
     *
     * @see fighting.FightAction#ended()
     */
    @Override
    public void ended() {
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

        if (!getSelf().getRoom().getMobs().contains(getTarget())) {
            out(getTarget().getId() + " is no longer here to attack!");
            getSelf().getFight().stopFighting();
            finished();
            return;
        }


        if (DiceRoll.ONE_D100.rollLessThan(50)) {
            miss();
        } else {
            hit();
        }

        Prompt.show(getSelf());

        durationMillis(2100);


        if (getSelf().isPlayer()) {

            tryToStandUp();
        } else if (DiceRoll.ONE_D100.rollLessThan(10)) {
            CommandProvider.getCommand(Stand.class).execute(getSelf(), null);
        }

    }

    private void tryToStandUp() {
        if (!getSelf().getMobStatus().isGroundFighting()) {

            if (!getSelf().getPlayer().getConfig().getFightData().isFlagSet(FightEnum.GROUND)) {

                out("You try and stand to avoid continuing to ground fight");

                CommandProvider.getCommand(Stand.class).execute(getSelf(), null);

            }

        }
    }

    private void miss() {
        out(new Msg(getSelf(), getTarget(), DESCRIPTION_FAIL[attackType]));
    }

    private void hit() {

        BasicDamage damage = new BasicDamage();


        int level = getSelf().getMobLevel();
        int strength = 18;

        if (getSelf().isPlayer()) {
            level = getSelf().getPlayer().getData().getLevel();

            strength = getSelf().getPlayer().getAttributes().getSTR().getValue();
        }

        damage.setRoll(3, level, strength + position);

        out(new Msg(getSelf(), getTarget(), DESCRIPTION_APPLY[attackType]));
        DamageManager.deal(getSelf(), getTarget(), damage.roll());

    }

    @Override
    public boolean isGroundFighting() {
        return true;
    }

}
