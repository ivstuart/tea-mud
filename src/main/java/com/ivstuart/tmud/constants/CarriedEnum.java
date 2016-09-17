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

package com.ivstuart.tmud.constants;

/**
 * Created by Ivan on 28/08/2016.
 */
public enum CarriedEnum {

    UNBURDENED("unburdened.", 1),
    LOADED("loaded", 2),
    TAXED("taxed", 3),
    HEAVY("heavily burden", 4),
    NOMOVE("can not move", 100);

    private final String desc;
    private final int movemod;

    CarriedEnum(String desc, int i) {
        this.desc = desc;
        this.movemod = i;
    }

    public static CarriedEnum get(int index) {
        return (CarriedEnum.values()[index]);
    }

    public String getDesc() {
        return desc;
    }

    public int getMovemod() {
        return movemod;
    }
}
