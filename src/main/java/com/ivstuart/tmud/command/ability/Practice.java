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
import com.ivstuart.tmud.state.mobs.Ability;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.mobs.Teacher;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Practice extends BaseCommand {

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob, String input) {

        // check do not already have skill

        if (!mob.getLearned().hasLearned(input)) {
            mob.out("You need to learn " + input + " in order to practice it");
            return;
        }

        // check has at least one learn available to spend
        if (mob.getPlayer().getData().getPracs() < 1) {
            mob.out("You have no pracs to practice with " + input);
            return;
        }

        // check teacher in the room
        Teacher teacher = mob.getRoom().getFirstTeacher();

        if (null == teacher) {
            mob.out("No teacher here to practice from");
            return;
        }
        String ability = input;

        if (!teacher.teachesEverything()) {

            ability = teacher.getAbility(input);

            if (null == ability) {
                mob.out("Teacher does not teach " + input);
                return;
            }
        }

        // Check skill or spell is available in this world

        Ability theAbility = mob.getLearned().getAbility(ability);

        if (theAbility.isNull()) {
            mob.out("There is no ability " + input + " to learn");
            return;
        }

        if (theAbility.practice(mob.getPlayer())) {
            mob.out("You practice " + theAbility);
            mob.getPlayer().getData().prac();
            return;
        }

        mob.out("You can not practice " + theAbility + " anymore!");

    }

}
