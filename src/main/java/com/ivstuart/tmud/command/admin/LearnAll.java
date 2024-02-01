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

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.state.mobs.Ability;
import com.ivstuart.tmud.state.skills.BaseSkill;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.skills.Spell;
import com.ivstuart.tmud.world.World;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LearnAll extends AdminCommand {


    @Override
    public void execute(Mob mob, String input) {

        super.execute(mob, input);

        mob.out("This is a test command to learn all skills and spells to 105% in the game");

        // Learn all skills
        for (BaseSkill skill : World.getSkills().values()) {
            Ability ability = new Ability(skill.getName());
            ability.setSkill(105);
            learn(mob, ability);
        }

        // Learn all skills
        for (Spell spell : World.getSpells().values()) {
            Ability ability = new Ability(spell.getName());
            ability.setSkill(105);
            learn(mob, ability);
        }

    }

    private void learn(Mob mob, Ability ability) {

        if (mob.getLearned().hasLearned(ability.getId())) {
            mob.out("You already know " + ability.getId());

        } else {
            mob.getLearned().add(ability);
            mob.out("You learn " + ability.getId());
        }
    }


}
