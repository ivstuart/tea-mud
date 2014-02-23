/*
 * Created on Nov 23, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ivstuart.tmud.person.carried;

import java.util.Collections;
import java.util.List;

/**
 * @author Ivan Stuart
 * 
 */
public class Money implements SomeMoney {

	private static final long serialVersionUID = 7446753180088885428L;

	public static final String[] desc = { "$CCopper$J", "$ESilver$J",
			"$BGold$J" };

	public static final int COPPER = 0;

	public static final int SILVER = 1;

	public static final int GOLD = 2;

	private int type;

	private int quantity;

	public Money(){};
	
	/**
	 * 
	 */
	public Money(int moneyType, int amount) {
		super();
		type = moneyType;
		quantity = amount;
	}

	/**
	 * @param money
	 */
	public void add(Money money) {
		if (money != null) {
			quantity += money.quantity;
		}
	}


	public void add(MoneyBag moneyBag) {
		moneyBag.add(this);
	}

	@Override
	public void add(SomeMoney cash) {
		System.out.println("Should never happen 1");
	}

	@Override
	public void clear() {
		quantity = 0;

	}

	@Override
	public List<SomeMoney> getList() {
		return Collections.emptyList();
	}

	@Override
	public int getValue() {
		return ((10 ^ type) * quantity);
	}

	@Override
	public boolean isBag() {
		return false;
	}

	/**
	 * @return
	 */
	@Override
	public boolean isEmpty() {
		return (quantity == 0);
	}

	/**
	 * @param money
	 * @return
	 */
	public boolean isSameType(Money money) {
		return (money.type == type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see person.carried.SomeMoney#remove(person.carried.Money)
	 */
	@Override
	public boolean remove(Money money) {
		if (money.type != type) {
			return false;
		}
		if (money.quantity > quantity) {
			return false;
		}
		quantity -= money.quantity;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see person.carried.SomeMoney#remove(person.carried.MoneyBag)
	 */
	@Override
	public boolean remove(MoneyBag cash) {
		return cash.remove(this);
	}

	@Override
	public String toString() {
		String temp = String.valueOf(quantity);
		temp += " " + desc[type] + " coin";
		if (quantity != 1) {
			temp += "s";
		}
		return temp;
	}

}
