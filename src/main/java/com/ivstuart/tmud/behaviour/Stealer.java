/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Stealer extends BaseBehaviour {

    private static Logger LOGGER = LogManager.getLogger();


    public Stealer() {
        parameter = 50;
    }

    @Override
    public String getId() {
        return mob.getId();
    }

    @Override
    public boolean tick() {

        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName()+" is does not feel like stealing");
            return false;
        }

        List<Mob> mobs = mob.getRoom().getMobs();

        int randomIndex = (int) Math.random() * mobs.size();

        Mob victim = mobs.get(randomIndex);

        if (victim == mob) {
            LOGGER.debug("Will not steal from self");
            return false;
        }

        SomeMoney money = new Money(Money.COPPER,5);

        if (!victim.getInventory().getPurse().remove(money)) {
            LOGGER.debug("NO money to steal");
            return false;
        }

        mob.getInventory().add(money);

        return false;
    }

}
