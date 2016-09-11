/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.person.statistics.affects;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.ivstuart.tmud.constants.SpellNames.DETECT_INVISIBLE;

public class DetectAffect extends Affect {

	private static final Logger LOGGER = LogManager.getLogger();
	private final Spell spell;

	public DetectAffect(Mob mob_, String desc_, int duration_) {
		super(mob_, desc_, duration_);
		this.spell = null;
	}

	public DetectAffect(Mob target_, Spell spell) {
		super(target_,spell.getId(),spell.getDuration().roll());
		this.spell = spell;
	}

	@Override
	public void applyEffect() {
		_mob.out("You feel the affects of " + _desc);

		// LOGGER.debug("Spell id :"+spell.getId());

		if (spell.getId().equalsIgnoreCase(DETECT_INVISIBLE)) {
			_mob.setDetectInvisible(true);
		}
	}

	@Override
	public void removeEffect() {
		_mob.out("The affects of " + _desc + " wear off");

		if (spell.getId().equalsIgnoreCase(DETECT_INVISIBLE)) {
			_mob.setDetectInvisible(false);
		}

	}

	public void setDuration(int duration) {
		this._duration = duration;
	}
}
