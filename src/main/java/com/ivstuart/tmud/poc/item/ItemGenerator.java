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

import com.ivstuart.tmud.poc.mob.RarityEnum;
import com.ivstuart.tmud.state.items.Armour;
import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.items.Weapon;
import com.ivstuart.tmud.state.mobs.Mob;

import java.util.Random;

public class ItemGenerator {

    /**
     *
     * @return
     */

    public static Item getItem() {
        // Use its rarity, type and level to roll an item.

        // Animals only provide fur meat claws bones

        // Humanoid have armour weapons and cash

        // hat and helm worn on head

        Item item = selectRandomClass();

        RarityEnum rarity = RarityEnum.getRandom();

        addMagicBonuses(item,rarity);

        return item;
    }

    private static void addMagicBonuses(Item item, RarityEnum rarity) {
        for (int counter=0;counter<rarity.getBonuses();counter++) {
            addRandomBonus(item);
        }
    }

    private static void addRandomBonus(Item item) {
        int saves = item.getSave();
        item.setSave(++saves);
    }

    private static Item selectRandomClass() {
        Random random = new Random();

        Item item = null;
        switch(random.nextInt(6)) {
            case 0:
                item = createArmour();
                break;
            case 1:
                item = createWeapon();
                break;
            case 2:
                item = createWeapon();
                break;
            case 3:
                item = createWeapon();
                break;
            case 4:
                item = createWeapon();
                break;
            case 5:
                item = createWeapon();
                break;

        }
        return item;
    }

    private static Item createWeapon() {
        Weapon weapon = new Weapon();
        weapon.setId("b sword");
        weapon.setAlias("b sword");
        weapon.setBrief("b rusty sword");
        weapon.setLong("b rusty sword which looks well worn");
        weapon.setLook("b sword");


        weapon.setWear("PRIMARY SECONDARY");
        weapon.setWeight(2);
        weapon.setCost(10);
        weapon.setRent(5);

        weapon.setDamage("1d6+1");
        weapon.setType("weapon SHARP");
        weapon.setSkill("slashing");

        return weapon;

    }

    private static Item createArmour() {
        Armour armour = new Armour();
        armour.setId("scarf");
        armour.setAlias("scarf");
        armour.setBrief("red scarf");
        armour.setLong("red scarf with flowers");
        armour.setLook("scarf");

        armour.setEffects("3");
        armour.setWear("HEAD NECK");
        armour.setWeight(2);
        armour.setCost(10);
        armour.setRent(5);

        armour.setArmour("0 2 0 0 0 0 0");

        return armour;
    }

}
