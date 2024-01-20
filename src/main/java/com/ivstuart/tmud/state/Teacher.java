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

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.world.World;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Teacher extends Prop {

    private final List<String> _abilities;

    public Teacher() {
        _abilities = new ArrayList<>();
    }

    public Teacher(Teacher teacher_) {
        super(teacher_);

        _abilities = teacher_._abilities;
    }

    public String displayAbilities() {

        StringBuilder sb = new StringBuilder();

        if (teachesEverything()) {

            for (BaseSkill skill : World.getSkills().values()) {

                sb.append(skill.getId() + "\n");

            }

            for (Spell spell : World.getSpells().values()) {

                sb.append(spell.getId() + "\n");

            }

            return sb.toString();
        } else {
            for (Object obj : _abilities) {
                sb.append(obj.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    public String getAbility(String ability_) {

        for (String ability : _abilities) {
            if (ability.startsWith(ability_)) {
                return ability;
            }
        }
        return null;
    }

    @Override
    public boolean isTeacher() {
        return true;
    }

    public void setAbility(String ability_) {
        _abilities.add(ability_);
    }

    public boolean teachesEverything() {
        return (_abilities.isEmpty());
    }
}
