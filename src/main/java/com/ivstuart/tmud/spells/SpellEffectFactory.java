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

package com.ivstuart.tmud.spells;

import java.util.HashMap;
import java.util.Map;

public class SpellEffectFactory {

    public final static SpellEffect DAMAGE = new Damage();
    private static final Map<String, SpellEffect> spellEffectMap = new HashMap<String, SpellEffect>();

    static {
        spellEffectMap.put("HEAL", new Heal());
        spellEffectMap.put("POISON", new Poison());
        spellEffectMap.put("CURE", new CurePoison());
        spellEffectMap.put("CUREDIS", new CureDisease());
        spellEffectMap.put("ARMOUR", new Protection());
        spellEffectMap.put("BLUR", new Blur());
        spellEffectMap.put("SANC", new Sanctuary());
        spellEffectMap.put("RECALL", new Recall());
        spellEffectMap.put("SUMMON", new Summon());
        spellEffectMap.put("BUFF", new BuffStats());
        spellEffectMap.put("DEBUFF", new BuffStats());
        spellEffectMap.put("FLY", new Levitate());
        spellEffectMap.put("DETECT", new DetectInvisible());
        spellEffectMap.put("INVIS", new Buff());
        spellEffectMap.put("SLEEP", new Sleep());
        spellEffectMap.put("REFRESH", new Refresh());
        spellEffectMap.put("IDENT", new Identify());
        spellEffectMap.put("CREATE-LIGHT", new CreateLight());
        spellEffectMap.put("CREATE-FOOD", new CreateFood());
        spellEffectMap.put("CREATE-WATER", new CreateWater());
        spellEffectMap.put("ENCHANT", new EnchantWeapon());
        spellEffectMap.put("CONTROL-WEATHER", new ControlWeather());
        spellEffectMap.put("CLONE", new CloneMob());
        spellEffectMap.put("BLINDNESS", new Blindness());
        spellEffectMap.put("CURE-BLINDNESS", new CureBlindness());
        spellEffectMap.put("ANIMATE-DEAD", new AnimateDead());

        spellEffectMap.put("CHARM", new Charm());
        spellEffectMap.put("LOCATE", new LocateObject());
    }

    public static SpellEffect get(String spell) {

        return spellEffectMap.get(spell.toUpperCase());
    }

}
