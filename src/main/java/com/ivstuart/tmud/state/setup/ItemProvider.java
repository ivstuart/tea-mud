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

import com.ivstuart.tmud.state.items.*;
import com.ivstuart.tmud.world.World;

public class ItemProvider {

    /**
     * Used to provide initial equipment for a player.
     */
    public static void load() {

        Item item = new Item();
        createItem(item,"tinder-box-001", "small tinder-box", "small flint strike and lite tinder-box", "LIGHTER", 1);

        Torch torch = new Torch();
        createItem(torch,"torch-001","torch","wooden torch with a charred end","wooden torch with charred end which has a sticky oily substance on the end",1);
        torch.setEffects("20");
        torch.setWear("PRIMARY SECONDARY");

        Waterskin waterskin = new Waterskin();
        createItem(waterskin,"waterskin-001","waterskin canteen", "hide waterskin","drinkable",5);
        waterskin.setCost(10);
        waterskin.setRent(5);
        // poison, alcohol, food, thirst
        waterskin.setLiquidType("0:0:10:100");

        Weapon sword = new Weapon();
        createItem(sword,"sword-001","sword","rusty sword which looks well worn", "weapon SHARP",5);
        sword.setDamage("1d6+1");
        sword.setWear("PRIMARY SECONDARY");
        sword.setCost(10);
        sword.setRent(5);

        Weapon club = new Weapon();
        createItem(club,"club-001","club","rusty club which looks well worn", "weapon SHARP",5);
        club.setSkill("crushing");
        club.setDamage("1d6+2");
        club.setWear("PRIMARY SECONDARY");
        club.setCost(10);
        club.setRent(5);

        GemList gem = new GemList();
        createItem(gem,"immortal-80","immortal gem", "flawless immortal gemstone", "gem",1);
        gem.setMana("AIR WATER FIRE EARTH");
        gem.setLevel("80 80 80 80");
        gem.setWear("PRIMARY SECONDARY");

    }

    private static void createItem(Item item, String id, String alias, String look, String type, int weight) {
        item.setId(id);
        item.setAlias(alias);
        item.setShort(alias);
        item.setLook(look);
        item.setType(type);
        item.setWeight(weight);
        World.add(item);
    }



}
