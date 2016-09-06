/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.command.combat.Flee;
import com.ivstuart.tmud.common.DiceRoll;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Wimpy extends BaseBehaviour {

    private static Logger LOGGER = LogManager.getLogger();


    public Wimpy() {
        parameter = 50;
    }

    @Override
    public String getId() {
        return mob.getId();
    }

    @Override
    public boolean tick() {
        // If any players visible in same location then random pick one to attack.
        if (!mob.getFight().isFighting()) {
            LOGGER.debug(mob.getName() + " is not fighting hence will not wimpy");
            return false;
        }

        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName()+" is does not feel like being wimpy");
            return false;
        }

        // Absolute amount or percentage to use here. Coded as an absolute amount for now.
        if (parameter2 < mob.getHp().getValue()) {
            LOGGER.debug(mob.getName()+" wimpy not reached yet");
            return false;
        }

        CommandProvider.getCommand(Flee.class).execute(mob,null);

        return false;


    }

}
