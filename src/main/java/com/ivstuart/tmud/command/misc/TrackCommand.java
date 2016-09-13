/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.misc;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Track;

import static com.ivstuart.tmud.constants.SkillNames.TRACKING;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TrackCommand extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		if (!mob.getMv().deduct(1)) {
			mob.out("You have no movement left to track with");
			return;
		}

		// Success or fail
		Ability ability = mob.getLearned().getAbility(TRACKING);

        if (ability.isNull()) {
            mob.out("You have no such ability to do tracking");
            return;
        }

        if (ability.isSuccessful(mob)) {

			if (mob.getRoom().getTracks() == null) {
				mob.out("You fail to find any tracks");
				return;
			}

			for (Track track : mob.getRoom().getTracks()) {
				mob.out("Track off to the " + track.getDirection() + " for " + track.getWho() + " age " + track.getAge());
			}

		} else {
			mob.out("You fail to find any tracks");
		}
	}

}
