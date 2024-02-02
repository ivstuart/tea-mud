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

package com.ivstuart.tmud.state.mobs;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.MobState;
import com.ivstuart.tmud.common.Tickable;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.person.movement.MoveManager;
import com.ivstuart.tmud.person.statistics.diseases.Disease;
import com.ivstuart.tmud.state.places.RoomEnum;
import com.ivstuart.tmud.state.player.Attribute;
import com.ivstuart.tmud.world.MoonPhases;
import com.ivstuart.tmud.world.WeatherSky;
import com.ivstuart.tmud.world.World;

import java.util.List;

import static com.ivstuart.tmud.constants.SpellNames.UNDERWATER_BREATH;

public class MobTimePassing {

    private final Mob mob;

    private int fallingCounter;

    public MobTimePassing(Mob mob) {
        this.mob = mob;
        this.fallingCounter=0;
    }

    public void tick() {
        checkIdleTimeAndKickout();

        tickRegenerate();

        checkForDrowning();

        checkForFalling();

        checkHungerAndThirst();

        checkForDiseases();

        checkForMobAffects();

        checkForMobBehaviour();

        checkForHitByLightning();
    }


    private void checkForHitByLightning() {

        if (World.getWeather() == WeatherSky.LIGHTNING) {
            if (mob.getRoom().getSectorType().isInside() && DiceRoll.ONE_D100.rollLessThanOrEqualTo(1) &&
                    DiceRoll.ONE_D100.rollLessThanOrEqualTo(1)) {
                mob.out("You are hit by lightning!");
                mob.getMobCoreStats().getHealth().increasePercentage(-1 * DiceRoll.roll(2, 100, 0));
                DamageManager.checkForDefenderDeath(mob, mob);
            }
        }


    }

    private void checkForMobAffects() {
        if (null != mob.getMobAffects()) {
            mob.getMobAffects().tick();
        }
    }

    private void checkForMobBehaviour() {
        if (mob.getTickers() != null) {
            for (Tickable ticker : mob.getTickers()) {
                ticker.tick();
            }
        }
    }

    private void tickRegenerate() {

        MobState currentState = mob.getState();

        int regenRatePercentage = 3;
        if (mob.getRoom().hasFlag(RoomEnum.REGEN)) {
            regenRatePercentage *= 3;
        } else {
            if (!mob.getRoom().getSectorType().isInside() && MoonPhases.isNightTime()) {
                regenRatePercentage = regenRatePercentage * MoonPhases.getPhase().getManaMod();
            }
        }

        mob.getMobCoreStats().regen(currentState, regenRatePercentage);
    }

    private void checkForDrowning() {
        if (mob.getMobCoreStats().getHealth() != null) {

            if (mob.getRoom().hasFlag(RoomEnum.UNDER_WATER) &&
                    !mob.getRace().isWaterbreath() &&
                    !mob.getMobAffects().hasAffect(UNDERWATER_BREATH)) {
                mob.out("You begin to drown as you can not breath underwater");
                mob.getMobCoreStats().getHealth().increasePercentage(-20);
                DamageManager.checkForDefenderDeath(mob, mob);
            }
        }

    }

    private void checkForDiseases() {
        if (!mob.getMobBodyStats().isUndead()) {
            List<Disease> diseases = mob.getMobAffects().getDiseases();
            for (Disease disease : diseases) {
                if (disease.isDroplets()) {
                    // LOGGER.debug(getName()+" infecting mobs in room with "+disease.getDesc());
                    infectMobs(mob.getRoom().getMobs(), disease);
                }
                if (disease.isAirbourne() && DiceRoll.ONE_D100.rollLessThanOrEqualTo(disease.getInfectionRate())) {
                    mob.getRoom().add(disease);
                }
            }
        }
    }

    private void infectMobs(List<Mob> mobs, Disease disease) {

        for (Mob mob : mobs) {
            if (mob == this.mob) {
                continue;
            }
            Disease.infect(mob, disease);
        }

    }

    private void checkIdleTimeAndKickout() {
        if (mob.isPlayer() && !mob.getFight().isEngaged()) {
            mob.getPlayer().getConnection().checkTimeout(mob);
        }
    }

    private void checkHungerAndThirst() {
        if (mob.isPlayer() && !mob.getMobBodyStats().isUndead()) {
            Attribute thirst = mob.getPlayer().getData().getThirst();
            thirst.decrease(1);
            Attribute hunger = mob.getPlayer().getData().getHunger();
            hunger.decrease(1);
            if (thirst.getValue() <= 0) {
                mob.out("You hurt from thirst!");
                mob.getMobCoreStats().getHealth().increasePercentage(-15);
            }
            if (hunger.getValue() <= 0) {
                mob.out("You hurt from hunger!");
                mob.getMobCoreStats().getHealth().increasePercentage(-10);
            }

            Attribute drunk = mob.getPlayer().getData().getDrunkAttribute();
            Attribute poison = mob.getPlayer().getData().getPoisonAttribute();

            if (poison.getValue() > 0) {
                mob.out("You hurt from poison!");
                mob.getMobCoreStats().getHealth().increasePercentage(-10);
                poison.decrease(1);
            }

            if (drunk.getValue() > 0) {
                mob.out("You slowly sober up, coffee might help");
                drunk.decrease(1);
            }
            DamageManager.checkForDefenderDeath(mob, mob);
        }
    }

    private void checkForFalling() {
        if (mob.isFlying() && fallingCounter > 0) {
            fallingCounter = 0;
        }

        // Falling from the sky. 30% damage per room
        if (mob.getRoom().hasFlag(RoomEnum.AIR) && !mob.isFlying()) {
            mob.out("You are in free fall while not flying");
            fallingCounter++;
            MoveManager.fall(mob, "down");
        }

        if (!mob.getRoom().hasFlag(RoomEnum.AIR) && fallingCounter > 0) {
            mob.out("Ouch, you hit the ground hard!");
            if (mob.getRoom().hasFlag(RoomEnum.WATER)) {
                mob.getMobCoreStats().getHealth().increasePercentage(-10 * fallingCounter);
            } else {
                mob.getMobCoreStats().getHealth().increasePercentage(-30 * fallingCounter);
            }
            fallingCounter = 0;
            DamageManager.checkForDefenderDeath(mob, mob);
        }
    }
}
