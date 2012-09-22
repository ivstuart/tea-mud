/*
 * Created on Nov 23, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ivstuart.tmud.person.carried;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Ivan Stuart
 * 
 */
public class MoneyBag implements SomeMoney {

	private static final long serialVersionUID = 7350863760208369543L;

	private List<SomeMoney> list;

	/**
	 * 
	 */
	public MoneyBag() {
		super();
		list = new ArrayList<SomeMoney>();
	}

	public MoneyBag(int type, int amount) {
		this();
		list.add(new Money(type, amount));
	}

	public MoneyBag(SomeMoney moneyBag_) {
		super();
		list = new ArrayList<SomeMoney>(moneyBag_.getList());

	}

	@Override
	public void add(Money money) {
		ListIterator<SomeMoney> moneyItr = list.listIterator();

		while (moneyItr.hasNext()) {
			Money aMoney = (Money) moneyItr.next();
			if (aMoney.isSameType(money)) {
				aMoney.add(money);
				if (aMoney.isEmpty()) {
					moneyItr.remove();
				}
				return;
			}
		}
		moneyItr.add(money);
	}

	public void add(MoneyBag bag) {
		ListIterator<SomeMoney> moneyItr = bag.list.listIterator();

		while (moneyItr.hasNext()) {
			this.add((Money) moneyItr.next());
		}
	}

	@Override
	public void add(SomeMoney cash) {
		if (cash.isBag()) {
			this.add((MoneyBag) cash);
		} else {
			this.add((Money) cash);
		}

	}

	/**
	 * 
	 */
	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public List<SomeMoney> getList() {
		return list;
	}

	public Money getType(Money otherMoney) {

		for (SomeMoney someMoney : list) {

			Money money = ((Money) someMoney);

			if (money.isSameType(otherMoney)) {
				return money;
			}
		}

		return new Money(Money.COPPER, 0);
	}

	@Override
	public int getValue() {
		ListIterator<SomeMoney> moneyItr = list.listIterator();
		int total = 0;
		while (moneyItr.hasNext()) {
			Money aMoney = (Money) moneyItr.next();
			total += aMoney.getValue();
		}
		return total;
	}

	@Override
	public boolean isBag() {
		return true;
	}

	@Override
	public boolean isEmpty() {
		return (list.size() == 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see person.carried.SomeMoney#remove(person.carried.Money)
	 */
	@Override
	public boolean remove(Money someMoney) {
		ListIterator<SomeMoney> moneyItr = list.listIterator();
		while (moneyItr.hasNext()) {
			Money aMoney = (Money) moneyItr.next();
			if (aMoney.isSameType(someMoney)) {
				if (aMoney.remove(someMoney)) {
					validate();
					return true;
				}
				return false;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see person.carried.SomeMoney#remove(person.carried.MoneyBag)
	 */
	@Override
	public boolean remove(MoneyBag cash) {
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (SomeMoney money : list) {
			sb.append(money.toString()).append("\n");
		}

		return sb.toString();
	}

	private void validate() {
		ListIterator<SomeMoney> moneyItr = list.listIterator();

		while (moneyItr.hasNext()) {
			if (((Money) moneyItr.next()).isEmpty()) {
				moneyItr.remove();
			}
		}
	}

}
