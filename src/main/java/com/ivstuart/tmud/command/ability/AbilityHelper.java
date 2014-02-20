package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.MobStatus;

public class AbilityHelper {

	
	public static boolean canUseAbility(Mob self, Mob target, String ability) {
		
		if (!self.getState().canMove()) {
			// You must be able to move to bash someone
			self.out("You must be standing or flying to "+ability+" someone");
			return false;
		}

		MobStatus status = self.getMobStatus();

		if (status.isGroundFighting()) {
			self.out("You are ground fighting so can not "+ability+" someone");
			return false;
		}

		if (status.isImmobile()) {
			self.out("You are immobile so can not "+ability+" someone");
			return false;
		}

		if (status.isBashed()) {
			self.out("You are bashed so can not "+ability+" someone");
			return false;
		}
		
		return true;
	}
}
