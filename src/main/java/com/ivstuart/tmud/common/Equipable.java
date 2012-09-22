package com.ivstuart.tmud.common;

import java.util.List;

import com.ivstuart.tmud.state.Mob;

// TODO <T>
public interface Equipable extends Comparable {

	public void equip(Mob mob);

	public List<Integer> getWear();

	public int getWorn();

	public boolean setWorn(int location);

	public void unequip(Mob mob);
}
