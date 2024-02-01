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

import com.ivstuart.tmud.constants.ManaType;
import com.ivstuart.tmud.world.World;
import com.ivstuart.tmud.state.skills.Spell;

public class SpellProvider {

    public static void load() {

        loadCommonManaSpells();
        loadWaterManaSpells();
        loadFireManaSpells();
        loadAirManaSpells();
        loadEarthManaSpells();


    }
    private static void loadEarthManaSpells() {
        add(6,"poison",10,"","SINGLE",5, ManaType.EARTH, "POISON","", "1d6+10");
        add(6,"cure poison",10,"","SINGLE",5, ManaType.EARTH, "CURE","", "");
        add(8,"cure disease",10,"","SINGLE",5, ManaType.EARTH, "CURE","", "");
        add(4,"lesser healing",10,"2d6","SINGLE",5, ManaType.EARTH, "HEAL","", "");
        add(16,"healing touch",10,"3d6","SINGLE",5, ManaType.EARTH, "HEAL","", "");

        add(30,"greater healing",10,"4d6","SINGLE",5, ManaType.EARTH, "HEAL","", "");
        add(40,"mass healing",10,"3d6","AREA",5, ManaType.EARTH, "HEAL","", "");
        add(20,"stone skin",10,"","SINGLE",5, ManaType.EARTH, "BUFF","", "200");
        add(10,"earthen strength",10,"2","SINGLE",5, ManaType.EARTH, "BUFF","", "200");
        add(7,"detect poison",10,"","SINGLE",5, ManaType.EARTH, "BUFF","", "200");

        add(50,"insect swarm",10,"4d6","AREA",5, ManaType.EARTH, "","", "");
        add(60,"creeping doom",10,"6d6","AREA",5, ManaType.EARTH, "","", "");
    }

    private static void loadAirManaSpells() {
        add(5,"bless",10,"","SINGLE",5, ManaType.AIR, "BUFF","", "200");
        add(25,"levitate",10,"","SINGLE",5, ManaType.AIR, "FLY","", "200");

        add(6,"dust devil",10,"2d6","SINGLE",5, ManaType.AIR, "","", "");
        add(13,"lightning",10,"3d6","SINGLE",5, ManaType.AIR, "","", "");
        add(19,"vortex",10,"4d6","SINGLE",5, ManaType.AIR, "","dust devil", "");
        add(30,"whirlwind",10,"5d6","SINGLE",5, ManaType.AIR, "","vortex", "");
        add(45,"chain lightning",10,"3d6","AREA",5, ManaType.AIR, "","lightning", "");
        add(55,"cyclone",10,"4d6","AREA",5, ManaType.AIR, "","whirlwind", "");

    }

    private static void loadFireManaSpells() {
        add(8,"burning hands",10,"2d6","SINGLE",5, ManaType.FIRE, "","", "200");
        add(18,"flame ray",10,"3d6","SINGLE",5, ManaType.FIRE, "","burning hands", "200");
        add(28,"fireball",10,"2d6","AREA",5, ManaType.FIRE, "","flame ray", "200");
        add(38,"flame strike",10,"3d6","AREA",5, ManaType.FIRE, "","fireball", "200");
        add(48,"meteor storm",10,"4d6","AREA",5, ManaType.FIRE, "","fireball", "200");

        add(55,"disintegrate",10,"5d6","SINGLE",5, ManaType.FIRE, "","flame ray", "200");
        add(45,"searing orb",10,"4d6","SINGLE",5, ManaType.FIRE, "","", "200");
        add(75,"falling star",10,"6d6","SINGLE",5, ManaType.FIRE, "","", "200");
        add(3,"detect invisible",10,"","SINGLE",5, ManaType.FIRE, "","", "200");
        add(7,"detect hidden",10,"","SINGLE",5, ManaType.FIRE, "","", "200");

        add(11,"infravision",10,"","SINGLE",5, ManaType.FIRE, "","", "200");
        add(20,"blindness",10,"","SINGLE",5, ManaType.FIRE, "","", "200");
        add(10,"cure blindness",10,"","SINGLE",5, ManaType.FIRE, "","", "200");
    }

    private static void loadWaterManaSpells() {
        add(3,"create water",10,"","SINGLE",5, ManaType.WATER, "WATER","", "200");
        add(1,"chill touch",10,"2d6","SINGLE",5, ManaType.WATER, "","", "200");
        add(11,"ice lance",10,"2d6+10","SINGLE",5, ManaType.WATER, "","", "200");
        add(11,"ice blast",10,"2d6+10","SINGLE",5, ManaType.WATER, "","", "200");
        add(14,"acid arrow",10,"2d6+20","SINGLE",5, ManaType.WATER, "","", "200");
        add(20,"acid rain",10,"3d6","AREA",5, ManaType.WATER, "","", "200");

        add(30,"cone of cold",10,"3d6","SINGLE",5, ManaType.WATER, "","", "200");
        add(5,"blur",10,"10","SELF",5, ManaType.WATER, "","", "200");
        add(15,"protection",10,"20","SINGLE",5, ManaType.WATER, "","blur", "200");
        add(30,"sanctuary",10,"50","SINGLE",5, ManaType.WATER, "","protection", "200");
        add(28,"barrier",10,"50","SINGLE",5, ManaType.WATER, "","", "200");

        add(40,"invisibility",10,"","SINGLE",5, ManaType.WATER, "","", "200");
        add(8,"weaken",10,"","SINGLE",5, ManaType.WATER, "","", "200");
        add(22,"sleep",10,"","SINGLE",5, ManaType.WATER, "","", "200");
        add(80,"improved sleep",10,"","SINGLE",5, ManaType.WATER, "","sleep", "200");
        add(19,"water walking",10,"","SINGLE",5, ManaType.WATER, "","", "200");
    }

    private static void loadCommonManaSpells() {
        // Common Mana
        add(1,"magic missile",5,"2d6","SELF",5, ManaType.COMMON, "","", "");
        add(5,"magic bolt",10,"3d6","SELF",7, ManaType.COMMON, "","magic missile", "");
        add(9,"magic blast",20,"4d6","SELF",8, ManaType.COMMON, "","magic bolt", "");

        add(14,"combat sense",15,"","SINGLE",5, ManaType.COMMON, "BUFF","", "3d6+40");
        add(10,"word of recall",15,"","SELF",5, ManaType.COMMON, "","", "");

        add(40,"summon",40,"","ANY",5, ManaType.COMMON, "SUMMON","word of recall", "");

        add(15,"refresh",25,"","SINGLE",5, ManaType.COMMON, "REFRESH","", "");

        add(10,"identify",25,"","ITEM",5, ManaType.COMMON, "IDENTIFY","", "");

        add(1,"create light",10,"","SINGLE",5, ManaType.COMMON, "LIGHT","", "200");
        add(1,"create food",10,"","SINGLE",5, ManaType.COMMON, "FOOD","", "200");

        add(10,"enchant weapon",10,"","ITEM",5, ManaType.COMMON, "ENCHANT","", "200");
        add(15,"locate object",10,"","ITEM",5, ManaType.COMMON, "LOCATE","", "");
        add(50,"animate dead",10,"","ITEM",5, ManaType.COMMON, "ANIMATE","", "200");
        add(80,"control weather",10,"","SINGLE",5, ManaType.COMMON, "WEATHER","", "200");
        add(90,"clone mob",10,"","SINGLE",5, ManaType.COMMON, "CLONE","", "200");
        add(20,"charm",10,"","SINGLE",5, ManaType.COMMON, "CHARM","", "200");
    }

    private static void add(int level,
                            String name,
                            int cost,
                            String amount,
                            String target,
                            int speed,
                            ManaType manaType,
                            String effect,
                            String prerequsite,
                            String duration) {

        Spell spell = new Spell();
        spell.setId(name);
        spell.setLevel(level);
        spell.setCost(cost);
        if (!amount.isEmpty()) {
            spell.setAmount(amount);
        }
        spell.setTarget(target);
        spell.setSpeed(speed);
        spell.setMana(manaType);
        spell.setSpellEffect(effect);
        spell.setPrereq(prerequsite);
        if (!duration.isEmpty()) {
            spell.setDuration(duration);
        }
        World.add(spell);


    }
}


