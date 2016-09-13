/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.common.Equipable;

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
