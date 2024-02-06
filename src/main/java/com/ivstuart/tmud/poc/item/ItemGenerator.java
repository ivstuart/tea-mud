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
import com.ivstuart.tmud.state.items.*;

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
                item = createArmour(); // TODO add weighting for 80% armour
                break;
            case 1:
                item = createWeapon();
                break;
            case 2:
                item = createFood();
                break;
            case 3:
                item = createGem();
                break;
            case 4:
                item = createWaterskin();
                break;
            case 5:
                item = createTorch();
                break;

        }
        return item;
    }

    private static Item createTorch() {

        Random random = new Random();
        int choice = random.nextInt(2);

        Torch torch = new Torch();
        if (choice == 0) {
            torch.setId("torch");
            torch.setAlias("torch");
            torch.setLook("wooden torch with charred end which has a sticky oily substance on the end");
            torch.setShort("wooden torch with a charred end");
        }
        else {
            torch.setId("orb-of-light-001");
            torch.setAlias("orb light");
            torch.setLook("an aura of light coming from a glowing orb");
            torch.setShort("an aura of light coming from a glowing orb");
            torch.setWear("AURA");
        }
        return torch;
    }

    private static Item createWaterskin() {
        Waterskin waterskin = new Waterskin();
        waterskin.setLiquidType("0:0:10:100");
        waterskin.setId("waterskin");
        waterskin.setAlias("waterskin");
        waterskin.setShort("waterskin");
        waterskin.setLook("waterskin");
        waterskin.setWeight(5);
        waterskin.setCost(10);
        waterskin.setRent(5);
        return waterskin;
    }

    private static Item createGem() {
        Gem gem = new Gem();
        gem.setId("fire-20");
        gem.setAlias("ruby gem");
        gem.setShort("rough ruby gemstone");
        gem.setLook("rough ruby gemstone");
        return gem;
    }

    private static Item createFood() {
        Food food = new Food();
        food.setCookable(true);
        food.setId("bread");
        food.setAlias("bread");
        food.setWeight(1);
        food.setType("food");
        return food;
    }

    private static Item createWeapon() {
        Weapon weapon = new Weapon();
        weapon.setId("sword");
        weapon.setAlias("sword");
        weapon.setBrief("rusty sword");
        weapon.setVerbose("rusty sword which looks well worn");
        weapon.setLook("sword");

        // Set defaults on weapon now
        // weapon.setWear("PRIMARY SECONDARY");
        weapon.setWeight(2);
        weapon.setCost(10);
        weapon.setRent(5);

        weapon.setDamage("1d6+1");
        //weapon.setType("weapon SHARP");
        //weapon.setSkill("slashing");

        return weapon;

    }

    private static Item createArmour() {
        Armour armour = new Armour();
        armour.setId("scarf");
        armour.setAlias("scarf");
        armour.setBrief("red scarf");
        armour.setVerbose("red scarf with flowers");
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
