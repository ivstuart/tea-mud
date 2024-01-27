/*
 *  Copyright 2016. Ivan Stuart
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

package com.ivstuart.tmud.person.config;

import com.ivstuart.tmud.common.Colour;

import java.io.Serializable;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Random;

/*
 * This class is simple a data class to store the configuration
 * for the players character
 */
public class FightData implements Serializable {

    private static final long serialVersionUID = -4084243242625557108L;

    private final EnumSet<FightEnum> enumSet;

    public FightData() {
        enumSet = EnumSet.allOf(FightEnum.class);
    }

    public void flip(FightEnum fightEnum) {
        if (enumSet.contains(fightEnum)) {
            enumSet.remove(fightEnum);
        }
        else {
            enumSet.add(fightEnum);
        }
    }

    public boolean isFlagSet(FightEnum fightEnum) {
        return enumSet.contains(fightEnum);
    }

    public String getState(FightEnum fightEnum) {
        if (isFlagSet(fightEnum)) {
            return fightEnum.getOn();
        }
        else {
            return fightEnum.getOff();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (FightEnum fightEnum : FightEnum.values()) {
            sb.append(String.format("[ %1$10s ] %2$s\n",fightEnum.name(), getState(fightEnum)));
        }
        return sb.toString();
    }

    public FightEnum getRandomAttackType() {
        Random random = new Random();
        int size = enumSet.size();
        int index = random.nextInt(size);

        for (FightEnum fightEnum : enumSet) {
            index--;
            if (index <= 0) {
                return fightEnum;
            }
        }
        return FightEnum.BITE;

    }
}
