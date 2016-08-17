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

        System.out.println("Copper :"+copper);

        money.add(copper);

        System.out.println("Money :"+money);

        System.out.println("Copper :"+copper);

        money.remove(copper);

        System.out.println("Copper :"+copper);

        System.out.println("Money :"+money);
    }
}
