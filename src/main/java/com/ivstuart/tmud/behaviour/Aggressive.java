/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.WorldTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Aggressive extends BaseBehaviour {

    private static Logger LOGGER = LogManager.getLogger();


    public Aggressive() {
        parameter = 50; //aggro precentage
    }

    @Override
    public String getId() {
        return mob.getId();
    }

    @Override
    public boolean tick() {
        // If any players visible in same location then random pick one to attack.
        if (mob.getFight().isFighting()) {
            LOGGER.debug(mob.getName() + " is fighting and hence will not aggro");
            return false;
        }

        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName() + " is does not feel like being aggressive this tick");
            return false;
        }

        Room room = mob.getRoom();

        if (room == null) {
            LOGGER.warn(mob.getName() + " has no room");
            return false;
        }

        Mob target;
        if (parameter3 != null) {
            target = room.getMobs().get(parameter3);
        } else {
            target = room.getRandomPlayer();
        }

        if (target == null) {
            LOGGER.debug(mob.getName() + " has no players to attack");
            return false;
        }

        mob.getFight().getMelee().setTarget(target);

        WorldTime.addFighting(mob);

        return false;

    }

}
