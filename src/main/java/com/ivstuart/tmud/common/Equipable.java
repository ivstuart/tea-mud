package com.ivstuart.tmud.common;

import com.ivstuart.tmud.state.Mob;

import java.util.List;

// TODO <T>
public interface Equipable extends Comparable {

    void equip(Mob mob);

    List<Integer> getWear();

    int getWorn();

    boolean setWorn(int location);

    void unequip(Mob mob);

    int getAPB();
}
