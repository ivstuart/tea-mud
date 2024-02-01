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

import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.mobs.MobStatus;

public class AbilityHelper {


    public static boolean canUseAbility(Mob self, Mob target, String ability) {

        if (self.getState().stuck()) {
            // You must be able to move to bash someone
            self.out("You must be standing or flying to " + ability + " someone");
            return false;
        }

        MobStatus status = self.getMobStatus();

        if (status.isGroundFighting()) {
            self.out("You are ground fighting so can not " + ability + " someone");
            return false;
        }

        if (status.isImmobile()) {
            self.out("You are immobile so can not " + ability + " someone");
            return false;
        }

        if (status.isBashed()) {
            self.out("You are bashed so can not " + ability + " someone");
            return false;
        }

        return true;
    }
}
