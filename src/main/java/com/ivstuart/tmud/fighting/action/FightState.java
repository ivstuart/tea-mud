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

package com.ivstuart.tmud.fighting.action;

public enum FightState {

    BEGIN() {
        @Override
        public FightState next(FightAction action) {
            action.begin();
            return HAPPEN;
        }

    },
    HAPPEN() {
        @Override
        public FightState next(FightAction action) {
            action.happen();
            return DONE;
        }

    },
    CHANGED() {
        @Override
        public FightState next(FightAction action) {
            action.changed();
            return DONE;
        }
    },
    DONE() {
        @Override
        public FightState next(FightAction action) {
            action.ended();
            return FINISHED;
        }
    },
    FINISHED() {
        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public FightState next(FightAction action) {
            return FINISHED;
        }
    };

    public boolean isFinished() {
        return false;
    }

    public abstract FightState next(FightAction action);

}
