/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.common;

import com.ivstuart.tmud.state.Mob;

import java.util.List;
public interface Equipable extends Comparable {

    void equip(Mob mob);

    List<Integer> getWear();

    int getWorn();

    boolean setWorn(int location);

    void unequip(Mob mob);

    int getAPB();

    void increaseDamage();

    int getDamagedPercentage();
}
