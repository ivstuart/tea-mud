package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
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
    public void tick() {

        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName()+" is does not feel like stealing");
            return;
        }

        List<Mob> mobs = mob.getRoom().getMobs();

        int randomIndex = (int) Math.random() * mobs.size();

        Mob victim = mobs.get(randomIndex);

        if (victim == mob) {
            LOGGER.debug("Will not steal from self");
            return;
        }

        SomeMoney money = new Money(Money.COPPER,5);

        if (!victim.getInventory().getPurse().remove(money)) {
            LOGGER.debug("NO money to steal");
            return;
        }

        mob.getInventory().add(money);
    }

}
