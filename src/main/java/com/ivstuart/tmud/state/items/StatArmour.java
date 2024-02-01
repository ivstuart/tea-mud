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

import com.ivstuart.tmud.common.Equipable;
import com.ivstuart.tmud.state.items.Armour;
import com.ivstuart.tmud.state.mobs.Mob;

/**
 * Created by Ivan on 13/09/2016.
 */
public class StatArmour extends Armour implements Equipable {

    private String stat;
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    @Override
    public void equip(Mob mob) {
        super.equip(mob);
        mob.getPlayer().getAttributes().get(stat).increaseMaximum(amount);
        mob.getPlayer().getAttributes().get(stat).increase(amount);
    }

    @Override
    public void unequip(Mob mob) {
        super.unequip(mob);
        mob.getPlayer().getAttributes().get(stat).increaseMaximum(-amount);
        mob.getPlayer().getAttributes().get(stat).increase(-amount);
    }

}
