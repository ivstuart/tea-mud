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

package com.ivstuart.tmud.state.setup;

import com.ivstuart.tmud.state.skills.BaseSkill;
import com.ivstuart.tmud.world.World;

public class SkillsProvider {

    public static void load() {

        add(1,"kick");
        add(1,"piercing");
        add(1,"slashing");
        add(1,"crushing");
        add(1,"chopping");
        add(1,"thrusting");
        add(1,"whipping");
        add(1,"tackle");
        add(1,"unarmed");
        add(1,"climbing");
        add(1,"riding");

        add(1,"investigate");
        add(1,"scrolls", "MAGE");
        add(1,"wands", "MAGE");
        add(1,"concentration", "MAGE");

        add(1,"backstab", "THIEF");
        add(1,"circle", "THIEF");
        add(1,"steal", "THIEF");
        add(1,"pick lock", "THIEF");
        add(1,"hide", "THIEF");
        add(1,"sneak", "THIEF");

        add(1,"disarm", "FIGHTER");
        add(1,"shield block", "FIGHTER");

        add(1,"bash");
        add(1,"rescue");
        add(1,"searching");
        add(1,"swim");
        add(1,"butcher");
        add(1,"tracking");
        add(1,"second attack");
        add(1,"third attack");
        add(1,"dodge");
        add(1,"parry");
        add(1,"armour penetration");
        add(1,"enhanced damage");

        World.getSkill("third attack").setPrereq("second attack");
        World.getSkill("circle").setPrereq("backstab");
    }

    private static void add(int level, String name, String profession) {
        BaseSkill baseSkill = new BaseSkill(name);
        baseSkill.setLevel(level);
        baseSkill.setProf(profession);
        World.add(baseSkill);

    }

    private static void add(int level,String name) {
        BaseSkill baseSkill = new BaseSkill(name);
        baseSkill.setLevel(level);
        World.add(baseSkill);
    }
}
