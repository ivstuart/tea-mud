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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

/**
 * @author Ivan Stuart
 */
public class Money implements SomeMoney {

    public static final String[] desc = {"$CCopper$J", "$ESilver$J",
            "$BGold$J", "$EPlatinum$J"};
    public static final int COPPER = 0;
    public static final int SILVER = 1;
    public static final int GOLD = 2;
    public static final int PLATINUM = 3;
    public static final Money NO_MONEY = new Money(COPPER, 0);
    private static final long serialVersionUID = 7446753180088885428L;
    private static final Logger LOGGER = LogManager.getLogger();
    private int type;

    private int quantity;

    public Money() {
    }

    /**
     *
     */
    public Money(int moneyType, int amount) {
        super();
        type = moneyType;
        quantity = amount;
    }

    public Money(Money money) {
        type = money.type;
        quantity = money.quantity;
    }

    public static int getType(String input) {
        int type = Money.PLATINUM;
        if (input.contains("copper")) {
            type = Money.COPPER;
        }
        if (input.contains("silver")) {
            type = Money.SILVER;
        }
        if (input.contains("gold")) {
            type = Money.GOLD;
        }
        if (input.contains("plat")) {
            type = Money.PLATINUM;
        }
        return type;
    }

    /**
     * @param money
     */
    public boolean add(Money money) {

        if (money == null) {
            return false;
        }

        if (this.type != money.type) {
            return false;
        }

        quantity += money.quantity;

        return true;
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
        return (int) Math.pow(10, type) * quantity;
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

        LOGGER.debug("Current quantity is " + quantity + " new quantity is " + (quantity - money.quantity));

        this.quantity -= money.quantity;
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see person.carried.SomeMoney#remove(person.carried.MoneyBag)
     */
    @Override
    public boolean remove(MoneyBag cash) {
        for (SomeMoney money : cash.getList()) {
            if (!this.remove(money)) {
                return false;
            }
        }
        return true;
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
        return quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public SomeMoney removeAndConvert(int copper) {
        if (getValue() < copper) {
            return null;
        }
        int remainder = getValue() - copper;
        return new Money(remainder, Money.COPPER);
    }
}
