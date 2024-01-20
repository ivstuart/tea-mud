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


public enum SectorType {
    INSIDE(1),
    CITY(1),
    FIELD(2),
    FOREST(3),
    HILLS(4),
    MOUNTAIN(5),
    DESERT(5),
    WATER_SWIM(10),
    WATER_NO_SWIM(100),
    UNDERWATER(2),
    FLYING(1);

    private final int moveModify;

    SectorType(int moveModify) {
        this.moveModify = moveModify;
    }

    public int getMoveModify() {
        return moveModify;
    }

    public boolean isInside() {
        return this == INSIDE;
    }
}



