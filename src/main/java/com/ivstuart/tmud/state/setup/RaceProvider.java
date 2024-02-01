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

import com.ivstuart.tmud.state.player.Race;
import com.ivstuart.tmud.world.World;

public class RaceProvider {
    
    
    public static void load() {

        int id = 1;

        add(id++,"Human",true,"",0, false);
        add(id++,"Human",false,"",0, false);
        add(id++,"Half-Elf",true,"-1 0 1 1 -1",0, true);
        add(id++,"Troll",false,"3 5 -2 -3 -3",20, false);
        add(id++,"Wood-Elf",true,"-2 -2 3 3 0",0, true);
        add(id++,"Ogre",false,"3 5 -2 -3 -3",0, false);
        add(id++,"Storm-Giant",true,"3 5 -3 -3 -5",0, false);
        add(id++,"Orc",false,"2 2 -1 -2 -2",0, true);
        add(id++,"Stone-Giant",true,"3 3 -3 -5 -5",0, false);
        add(id++,"Half-Orc",false,"1 1 0 -1 -1",0, false);

        add(id++,"Dwarf",true,"1 1 0 -1 1",0, false);
        add(id++,"Skaven",false,"1 1 3 -1 -1",0, true);
        add(id++,"Centaur",true,"1 1 1 -1 -1",0, false);
        add(id++,"Drow",false,"-1 -1 1 1 1",0, true);
        add(id++,"Triton",true,"",0, false);
        add(id++,"Yuan-ti",false,"",0, false);
        add(id++,"Halfling",true,"-3 -3 3 1 1",0, false);
        add(id++,"Sahuagin",false,"",0, false);
        add(id++,"Gnome",true,"",0, false);
        add(id++,"Gnoll",false,"",0, false);

        add(id++,"Treeant",true,"",0, false);
        add(id++,"Bugbear",false,"",0, false);
        add(id++,"Sea-Sprite",true,"",0, false);
        add(id++,"Gargoyle",false,"",0, false);
        add(id++,"High-Elf",true,"",0, false);
        add(id++,"Wraith",false,"0 -2 -1 3 3",0, true);
        add(id++,"Fairy",true,"-2 -2 -1 5 3",0, false);
        add(id++,"Imp",false,"-2 -2 -1 5 3",0, true);
        add(id++,"Azer",true,"",0, false);
        add(id++,"Revenant",false,"2 3 1 -3 -3",0, true);

        add(id++,"Dryad",true,"",0, false);
        add(id++,"Ghoul",false,"2 3 1 -3 -3",0, true);
    }

    private static void add(int id,
                            String name,
                            boolean alignment,
                            String stats,
                            int regenHp,
                            boolean infravision) {
        Race race = new Race();
        race.setId(""+id);
        race.setName(name);
        race.setDesc(name);
        race.setAlignment(alignment);
        if (!stats.isEmpty()) {
            race.setStats(stats);
        }
        race.setRegenHp(regenHp);
        race.setArmour(0);
        race.setStoneskin(false);
        race.setInfravison(infravision);
        race.setWaterbreath(false);
        World.add(race);
    }
}


