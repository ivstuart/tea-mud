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

import com.ivstuart.tmud.constants.EquipLocation;
import com.ivstuart.tmud.state.items.Armour;
import com.ivstuart.tmud.state.items.BasicArmour;
import com.ivstuart.tmud.state.items.Item;

import java.util.ArrayList;
import java.util.List;

import static com.ivstuart.tmud.constants.EquipLocation.*;

public class ArmourProvider {

    private static final List<Item> list = new ArrayList<>();

    public static void populateList() {

        createArmour("orb-of-light-01","aura of light",AURA, 0);

        createArmour("wizard-hat-01","pointy hat",HEAD, 2);
        createArmour("helm-01","metal helm",HEAD,5);
        createArmour("cap-01","leather cap",HEAD, 3);

        createArmour("coif-01","chain-mail coif",NECK, 5);
        createArmour("scarf-01","silk scarf",NECK, 1);

        createArmour("warpaint-01","warpaint",FACE, 1);
        createArmour("mask-01","wooden mask",FACE, 1);
        createArmour("mask-02","bone mask of madness",FACE, 3);

        createArmour("ear-ring-01","runic ear ring",EAR, 1);
        createArmour("feather-01","blue feather",EAR, 1);

        createArmour("cloak-01","dark cloak",ABOUT_BODY, 3);
        createArmour("pauldron-01","leather pauldron",SHOULDER, 2);
        createArmour("robes-01","robes",BODY, 5);
        createArmour("shirt-01","plate-mail shirt",BODY, 9);
        createArmour("bracer-01","metal bracer",ARMS, 4);

        createArmour("ring-01","steel ring",FINGER, 1);
        createArmour("gauntlets-01","metal gauntlets",HANDS, 6);
        createArmour("gloves-01","red leather gloves",HANDS, 2);
        createArmour("bracelet-01","copper bracelet",WRIST, 1);
        createArmour("bracelet-02","wide steel bracelet",WRIST, 2);

        createArmour("belt-01","leather belt",WAIST, 2);
        createArmour("leggings-01","chain-mail leggings",LEGS, 8);
        createArmour("anklets-01","spiked anklets",ANKLE, 2);
        createArmour("boots-01","travelers boots",FEET, 5);

        createArmour("shield-01","round shield",PRIMARY, 4);
        createArmour("shield-02","tower shield",PRIMARY,  6);
        createArmour("shield-03","scintillating shield",PRIMARY, 8);

    }

    private static Item createArmour(String id, String alias, EquipLocation wear, int armourFactor) {

        BasicArmour armour = new BasicArmour(armourFactor);
        armour.setId(id);
        armour.setAlias(alias);
        armour.setBrief(alias);
        armour.setVerbose(alias);
        armour.setLook(alias);

        armour.setEffects("3");
        // TODO change this
        armour.setWear(String.valueOf(wear));
        armour.setWeight(2);
        armour.setCost(10);
        armour.setRent(5);

        list.add(armour);

        return armour;
    }

}
