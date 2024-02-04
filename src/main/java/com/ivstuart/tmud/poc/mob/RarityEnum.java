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

package com.ivstuart.tmud.poc.mob;

import java.util.Random;

public enum RarityEnum {

    COMMON (80,0),

    UNCOMMON(10,1),

    RARE(5,2),

    EPIC(3,3),

    LEGENDARY(1,4),

    MYTHICAL(1,5),

    UNIQUE(1,6);

    private final int weight;
    private final int bonuses;

    RarityEnum(int weight, int bonuses) {
        this.weight = weight;
        this.bonuses = bonuses;
    }

    public int getWeight() {
        return weight;
    }

    public int getBonuses() {
        return bonuses;
    }

    public static RarityEnum getIndex(int index) {
      return values()[index];
    }

    public static RarityEnum getRandom() {
        int sum = 0;
        for (RarityEnum rarityEnum: values()) {
            sum += rarityEnum.getWeight();
        }

        Random random = new Random();
        int result = random.nextInt(sum);

        int counter = 0;
        for (RarityEnum rarityEnum: values()) {
            result -= rarityEnum.getWeight();
            if (result <= 0) {
                return rarityEnum;
            }
            counter++;
        }
        return COMMON;

    }
}
