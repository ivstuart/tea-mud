package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.WorldTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Assist extends BaseBehaviour {

    private static Logger LOGGER = LogManager.getLogger();


    public Assist() {
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
            LOGGER.debug(mob.getName() + " is fighting already hence will not assist");
            return;
        }

        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName()+" is does not feel like assisting");
            return;
        }

        Room room = mob.getRoom();

        if (room == null) {
            LOGGER.warn(mob.getName() + " has no room to assist");
            return;
        }

        List<Mob> mobs = room.getMobs(mob.getName());
        mobs.remove(mob);

        Mob target = null;
        for (Mob fellowMob : mobs) {
            if (fellowMob.getFight().isFighting()) {
                target = fellowMob.getFight().getTarget();
            }
        }

        if (target == null) {
            LOGGER.debug(mob.getName() + " has no one to assist");
            return;
        }

        mob.getFight().getMelee().setTarget(target);

        WorldTime.addFighting(mob);


    }

}
