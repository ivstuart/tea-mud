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

package com.ivstuart.tmud.state.items;

public class BasicArmour extends Item {

    private static final long serialVersionUID = 1L;

    private final int armourFactor;

    public BasicArmour(int armourFactor) {
        this.armourFactor = armourFactor;
    }

    public int getArmourFactor() {
        return armourFactor * ((100 - super.damagedPercentage) / 100);
    }

    @Override
    public String toString() {
        return "BasicArmour{" +
                "armourFactor=" + armourFactor +
                '}'+ super.toString();
    }
}
