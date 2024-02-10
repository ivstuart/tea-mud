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

package com.ivstuart.tmud.poc.item;

import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.items.Weapon;

import java.util.ArrayList;
import java.util.List;

public class WeaponProvider {

    private static final List<Item> list = new ArrayList<>();

    public static void populateList() {
        createWeapon("wand","1d6",1, "casting");
        createWeapon("staff","2d4",3, "crushing");
        createWeapon("dagger","1d4",1, "piercing");
        createWeapon("spear","1d6",2, "piercing");
        createWeapon("sword","1d6+1",3, "slashing");
        createWeapon("axe","1d8",4, "chopping");
        createWeapon("club","1d4+1",2, "crushing");
        createWeapon("mace","1d6",3, "crushing");
        createWeapon("flail","1d6",3, "crushing");
        createWeapon("morning star","1d6",4, "crushing");
        createWeapon("bow","1d6",1, "ranged");
        createWeapon("whip","1d6",1, "whipping");
    }

    private static void createWeapon(String description, String damage, int weight, String skill) {
        Weapon weapon = new Weapon();
        weapon.setId(description);
        weapon.setAlias(description);
        weapon.setBrief(description);
        weapon.setVerbose(description);
        weapon.setLook(description);
        weapon.setWeight(weight);
        weapon.setCost(10);
        weapon.setRent(5);

        weapon.setDamage(damage);
        weapon.setSkill(skill);

        list.add(weapon);
    }
}
