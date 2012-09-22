package com.ivstuart.tmud.fighting;

/*
 * Note: damage = roll + damage skill + stats / 15
 * When applying damage take off armour.
 */
public interface Damage {

	public String getDescription();

	public String getType();

	// public Ability getAbility();

	public int roll();

	public void setRoll(int number, int sides, int adder);

}
