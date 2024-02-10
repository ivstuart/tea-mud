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

import com.ivstuart.tmud.state.items.Armour;
import com.ivstuart.tmud.state.items.Item;

import java.util.ArrayList;
import java.util.List;

public class ArmourProvider {

    private static final List<Item> list = new ArrayList<>();

    public static void populateList() {

        createArmour("orb-of-light-01","aura of light","AURA", "0 0 0 0 0 0 0");

        createArmour("wizard-hat-01","pointy hat","HEAD", "2 0 0 0 0 0 0");
        createArmour("helm-01","metal helm","HEAD", "5 0 0 0 0 0 0");
        createArmour("cap-01","leather cap","HEAD", "3 0 0 0 0 0 0");

        createArmour("coif-01","chain-mail coif","NECK", "0 5 0 0 0 0 0");
        createArmour("scarf-01","silk scarf","HEAD NECK", "0 1 0 0 0 0 0");

        createArmour("warpaint-01","warpaint","FACE", "0 1 0 0 0 0 0");
        createArmour("mask-01","wooden mask","FACE", "0 1 0 0 0 0 0");
        createArmour("mask-02","bone mask of madness","FACE", "0 3 0 0 0 0 0");

        createArmour("ear-ring-01","runic ear ring","EAR", "0 1 0 0 0 0 0");
        createArmour("feather-01","blue feather","EAR", "0 1 0 0 0 0 0");

        createArmour("cloak-01","dark cloak","ABOUT_BODY", "0 0 0 0 0 0 3");
        createArmour("pauldron-01","leather pauldron","SHOULDER", "0 1 2 0 0 0 0");
        createArmour("robes-01","robes","BODY", "0 5 0 0 0 0 0");
        createArmour("shirt-01","plate-mail shirt","BODY", "0 9 0 0 0 0 0");
        createArmour("bracer-01","metal bracer","ARMS", "0 4 0 0 0 0 0");

        createArmour("ring-01","steel ring","FINGER", "0 0 1 0 0 0 0");
        createArmour("gauntlets-01","metal gauntlets","HANDS", "0 0 6 0 0 0 0");
        createArmour("gloves-01","red leather gloves","HANDS", "0 0 2 0 0 0 0");
        createArmour("bracelet-01","copper bracelet","WRIST", "0 0 1 0 0 0 0");
        createArmour("bracelet-02","wide steel bracelet","WRIST", "0 0 2 0 0 0 0");

        createArmour("belt-01","leather belt","WAIST", "0 0 0 2 0 0 0");
        createArmour("leggings-01","chain-mail leggings","LEGS", "0 0 0 0 8 0 0");
        createArmour("anklets-01","spiked anklets","ANKLE", "0 0 0 0 0 2 0");
        createArmour("boots-01","travelers boots","FEET", "0 0 0 0 0 5 0");

        createArmour("shield-01","round shield","PRIMARY SECONDARY", "0 0 0 0 0 0 4");
        createArmour("shield-02","tower shield","PRIMARY SECONDARY", "0 0 0 0 0 0 6");
        createArmour("shield-03","scintillating shield","PRIMARY SECONDARY", "0 0 0 0 0 0 8");

    }

    private static Item createArmour(String id, String alias, String wear, String armourString) {

        Armour armour = new Armour();
        armour.setId(id);
        armour.setAlias(alias);
        armour.setBrief(alias);
        armour.setVerbose(alias);
        armour.setLook(alias);

        armour.setEffects("3");
        armour.setWear(wear);
        armour.setWeight(2);
        armour.setCost(10);
        armour.setRent(5);

        armour.setArmour(armourString);

        list.add(armour);

        return armour;
    }

}
