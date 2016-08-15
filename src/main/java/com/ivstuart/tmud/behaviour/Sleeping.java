package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.MobState;
import com.ivstuart.tmud.common.Tickable;
import com.ivstuart.tmud.state.Mob;
import org.apache.log4j.Logger;

public class Sleeping extends BaseBehaviour {

	private static Logger LOGGER = Logger.getLogger(Sleeping.class);

	public Sleeping() {
	}

	@Override
	public String getId() {
		return mob.getId();
	}

	@Override
	public void tick() {

		// If not engage and if damaged then sleep
		if(mob.getFight().isEngaged()) {
			LOGGER.debug("Sleeper mob is fighting so it will not sleep");
			return;
		}

		if(mob.getHp().isMaximum()) {
			return;
		}
		else {
			mob.setState(MobState.SLEEP);
		}
		// If nighttime 10 % chance of sleeping else 10 % of awaking
		if (DiceRoll.ONE_D100.rollMoreThan(10)) {
			return;
		}

		// If state awake and not fighting then change state to sleeping.
		mob.setState(MobState.SLEEP);




	}

}
