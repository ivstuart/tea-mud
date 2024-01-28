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

package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;

public class Wimpy extends BaseCommand {

    /**
     * Set a value or percentage of health to automatically trigger fleeing from
     * combat
     */
    @Override
    public void execute(Mob mob, String input) {

        if (input.isEmpty()) {
            mob.out("Wimpy followed by the number of health points at which to flee");
        }

        try {
            int wimpy = Integer.parseInt(input);

            mob.out("Wimpy set to " + wimpy);

            mob.getMobCombat().setWimpy(wimpy);
        } catch (NumberFormatException e) {
            mob.out("Wimpy number formatting issue with input value " + input + ", number only please");
        }
    }

}