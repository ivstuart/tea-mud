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

package com.ivstuart.tmud.fighting.action;

import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.state.mobs.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class FightAction {

    private static final Logger LOGGER = LogManager.getLogger();
    private final Mob self;
    private long whenNextStateMillis;
    private FightState state;
    private Mob target;

    public FightAction(Mob me, Mob target) {
        this.self = me;
        this.target = target;
        this.state = FightState.BEGIN;
    }

    public void begin() {
        // Set melee target
        if (this.isMeleeEnabled()) {
            LOGGER.debug("Is melee so setting melee target for combat");
            getSelf().getFight().changeTarget(getTarget());
        } else {
            LOGGER.debug("Is zero damage action so not setting melee target for combat");
        }
    }

    public abstract void changed();

    public void destroy() {
    }

    protected void duration(long durationSeconds) {
        whenNextStateMillis = System.currentTimeMillis()
                + (durationSeconds * 1000);
    }

    protected void durationMillis(long durationMillis) {
        whenNextStateMillis = System.currentTimeMillis() + durationMillis;
    }

    public abstract void ended();

    public void finished() {
        setState(FightState.FINISHED);
    }

    protected Fight getFight() {
        return self.getFight();
    }

    protected MobMana getMobMana() {
        return self.getMana();
    }

    public Mob getSelf() {
        return self;
    }

    public Mob getTarget() {
        return target;
    }

    public void setTarget(Mob mob) {
        this.target = mob;
    }

    public abstract void happen();

    public boolean isFinished() {
        return state.isFinished();
    }

    public boolean isGroundFighting() {
        return false;
    }

    public boolean isMeleeEnabled() {
        return true;
    }

    public void next() {

        if (System.currentTimeMillis() < whenNextStateMillis) {
            return;
        }

        state = state.next(this);
    }

    protected void out(Msg output_) {
        // Need to think about hits to flee characters...
        self.getRoom().out(output_);
        // _self.out(output);
    }

    protected void out(String output) {
        self.out(new Msg(self, target, output));
        // self.out(output);
    }

    public void restart() {
        setState(FightState.BEGIN);
    }

    public void setState(FightState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        String output = "FightAction{" +
                "whenNextStateMillis=" + whenNextStateMillis +
                ", state=" + state;

        if (self != null) {
            output += ", self=" + self.getName();
        }

        if (target != null) {
            output += ", target=" + target.getName();
        }

        output += '}';
        return output;
    }
}