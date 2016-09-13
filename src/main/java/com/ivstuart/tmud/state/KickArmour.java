/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
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
