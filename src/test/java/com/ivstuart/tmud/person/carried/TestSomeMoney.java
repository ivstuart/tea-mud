package com.ivstuart.tmud.person.carried;

import org.junit.Test;

/**
 * Created by Ivan on 17/08/2016.
 */
public class TestSomeMoney {

    @Test
    public void addRemoveSomeCopper() {
        SomeMoney money = new MoneyBag();

        Money copper = new Money(Money.COPPER, 10);

        System.out.println("Copper :" + copper);

        money.add(copper);

        System.out.println("Money :" + money);

        System.out.println("Copper :" + copper);

        money.remove(copper);

        System.out.println("Copper :" + copper);

        System.out.println("Money :" + money);
    }

    @Test
    public void convertSomeMoney() {
        SomeMoney money = new MoneyBag();

        Money copper = new Money(Money.COPPER, 1234);

        MoneyBag moneyBag = new MoneyBag(copper.getValue(), Money.PLATINUM, true);

        System.out.println("Money :" + moneyBag);
    }

    @Test
    public void convertSomeMoneyGold() {
        SomeMoney money = new MoneyBag();

        Money copper = new Money(Money.COPPER, 1234);

        MoneyBag moneyBag = new MoneyBag(copper.getValue(), Money.GOLD, true);

        System.out.println("Money :" + moneyBag);
    }
}
