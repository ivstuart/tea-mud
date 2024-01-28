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

/*
 * Created on Nov 23, 2004
 *
 *  To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ivstuart.tmud.person.carried;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Ivan Stuart
 */
public class MoneyBag implements SomeMoney {

    private static final long serialVersionUID = 7350863760208369543L;

    private final List<SomeMoney> list;

    /**
     *
     */
    public MoneyBag() {
        list = new ArrayList<>();
    }

    public MoneyBag(int type, int amount) {
        this();
        list.add(new Money(type, amount));
    }

    public MoneyBag(SomeMoney moneyBag_) {
        super();
        list = new ArrayList<>(moneyBag_.getList());

    }

    public MoneyBag(int value, int type, boolean convert) {
        list = new ArrayList<>(4);
        int remainder = value;
        if (type == Money.PLATINUM) {
            remainder = create(remainder, Money.PLATINUM);
        }
        if (type >= Money.GOLD) {
            remainder = create(remainder, Money.GOLD);
        }
        if (type >= Money.SILVER) {
            remainder = create(remainder, Money.SILVER);
        }
        create(remainder, Money.COPPER);
    }

    private int create(int value, int currency) {
        int coins = value / (int) Math.pow(10, currency);
        if (coins > 0) {
            list.add(new Money(currency, coins));
            value -= coins * (int) Math.pow(10, currency);
        }
        return value;
    }

    @Override
    public boolean add(Money money) {
        ListIterator<SomeMoney> moneyItr = list.listIterator();

        while (moneyItr.hasNext()) {
            Money aMoney = (Money) moneyItr.next();
            if (aMoney.isSameType(money)) {
                aMoney.add(money);
                if (aMoney.isEmpty()) {
                    moneyItr.remove();
                }
                return true;
            }
        }
        list.add(new Money(money));
        return false;
    }

    public void add(MoneyBag bag) {

        for (SomeMoney someMoney : bag.list) {
            this.add((Money) someMoney);
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
        return (list.isEmpty());
    }

    /*
     * (non-Javadoc)
     *
     * @see person.carried.SomeMoney#remove(person.carried.Money)
     */
    @Override
    public boolean remove(Money someMoney) {
        for (SomeMoney money : list) {
            Money aMoney = (Money) money;
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
            sb.append(money.toString()).append(" ");
        }

        return sb.toString();
    }

    @Override
    public boolean remove(SomeMoney cash) {
        if (cash.isBag()) {
            return this.remove((MoneyBag) cash);
        } else {
            return this.remove((Money) cash);
        }
    }

    @Override
    public int getWeight() {
        int weight = 0;
        for (SomeMoney money : list) {
            weight += money.getWeight();
        }
        return weight;
    }

    @Override
    public SomeMoney removeAndConvert(int copper) {
        if (getValue() < copper) {
            return null;
        }
        int remainder = getValue() - copper;
        return new MoneyBag(remainder, Money.PLATINUM, true);
    }

    private void validate() {

        list.removeIf(SomeMoney::isEmpty);
    }

}
