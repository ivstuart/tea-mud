package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.Tickable;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.WorldTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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
    public void tick() {
        // If any players visible in same location then random pick one to attack.
        if (mob.getFight().isFighting()) {
            LOGGER.debug(mob.getName() + " is fighting and hence will not aggro");
            return;
        }

        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName()+" is does not feel like being aggressive this tick");
            return;
        }

        Room room = mob.getRoom();

        if (room == null) {
            LOGGER.warn(mob.getName() + " has no room");
            return;
        }

        // TODO aggro by alignment or other criteria
        Mob target = room.getRandomPlayer();

        if (target == null) {
            LOGGER.debug(mob.getName() + " has no players to attack");
            return;
        }

        mob.getFight().getMelee().setTarget(target);

        WorldTime.addFighting(mob);


    }

}
