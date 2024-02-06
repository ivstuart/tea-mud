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

import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.items.LiquidType;

import static com.ivstuart.tmud.state.items.LiquidType.WATER;

public class Waterskin extends Item {

    private static final long serialVersionUID = -4557556852795411856L;

    private int capacity = 4;
    private int drafts = 4;
    private LiquidType liquidType;

    public Waterskin() {
        this.setType("drinkable");

    }

    public LiquidType getLiquidType() {
        if (liquidType == null) {
            return WATER;
        }
        return liquidType;
    }

    public void setLiquidType(String liquidType) {
        String[] params = liquidType.split(":");
        this.liquidType = new LiquidType(
                Integer.parseInt(params[0]),
                Integer.parseInt(params[1]),
                Integer.parseInt(params[2]),
                Integer.parseInt(params[3]));
    }

    public void drink() {
        drafts--;
    }

    public void empty() {
        drafts = 0;
    }

    public void fill() {
        drafts = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity_) {
        capacity = capacity_;
    }

    public int getDrafts() {
        return drafts;
    }

    public void setDrafts(int drafts_) {
        drafts = drafts_;
    }

}
