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

/*
 * Created on 09-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.person;

import com.ivstuart.tmud.state.Ability;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Learned implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Map<String, Ability> abilities;

    public Learned() {
        // lazy initialisation of this as most mobs will have no skills.
        abilities = new HashMap<>();
    }

    public void add(Ability ability) {
        abilities.put(ability.getId(), ability);

    }

    public Collection<Ability> getAbilities() {
        return abilities.values();
    }

    public Ability getAbility(String name) {

        Ability ability = abilities.get(name);

        if (ability == null) {
            ability = Ability.NULL_ABILITY;
        }

        return ability;

    }

    /**
     * @param name
     * @return
     */
    public boolean hasLearned(String name) {
        return abilities.containsKey(name);
    }

    public boolean hasPrereq(String prereq) {

        if (prereq == null) {
            return true;
        }

        Ability ab = getAbility(prereq);

        if (null == ab || ab.isNull()) {
            return false;
        }

        return ab.getSkill() > 50;
    }

    public Ability remove(String name) {
        return abilities.remove(name);
    }

    public int getAbilityPoints() {
        int abilityPoints = 0;

        for (Ability ab : abilities.values()) {
            abilityPoints += ab.getSkill();
        }

        return abilityPoints;
    }
}
