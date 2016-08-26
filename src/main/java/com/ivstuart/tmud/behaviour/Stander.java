package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.command.state.Stand;
import com.ivstuart.tmud.common.DiceRoll;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Stander extends BaseBehaviour {

    private static Logger LOGGER = LogManager.getLogger();


    public Stander() {
        parameter = 50; // precentage
    }

    @Override
    public String getId() {
        return mob.getId();
    }

    @Override
    public void tick() {

        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName() + " is does not feel like standing");
            return;
        }

        if (!mob.getFight().isGroundFighting()) {
            LOGGER.debug(mob.getName() + " no need to stand");
            return;
        }

        if (mob.getMobStatus().isGroundFighting()) {
            LOGGER.debug(mob.getName() + " recently engaged in ground fighting");
            return;
        }

        CommandProvider.getCommand(Stand.class).execute(mob, null);

    }

}
