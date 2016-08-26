package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.DiceRoll;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Regen extends BaseBehaviour {

    private static Logger LOGGER = LogManager.getLogger();


    public Regen() {
        parameter = 50; // precentage
    }

    @Override
    public String getId() {
        return mob.getId();
    }

    @Override
    public void tick() {

        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName() + " is does not feel like regenerating");
            return;
        }

        if (mob.getHp().isMaximum()) {
            LOGGER.debug(mob.getName() + " has maximum health already");
            return;
        }

        mob.getHp().increase(parameter2);

        mob.getRoom().out(mob.getName() + " regenerates!");

    }

}
