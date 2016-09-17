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

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.common.Equipable;

/**
 * Created by Ivan on 13/09/2016.
 */
public class KickArmour extends Armour implements Equipable {

    private int kick;

    public int getKick() {
        return kick;
    }

    public void setKick(int kick) {
        this.kick = kick;
    }

    @Override
    public void equip(Mob mob) {
        super.equip(mob);
        mob.getEquipment().increaseKick(kick);
    }

    @Override
    public void unequip(Mob mob) {
        super.unequip(mob);
        mob.getEquipment().increaseKick(-kick);
    }

}
