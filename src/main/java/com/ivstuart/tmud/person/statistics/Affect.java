package com.ivstuart.tmud.person.statistics;

import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.constants.UsefulContants.affDuration;

public class Affect {

	// TODO remove mob from affect as the application of an affect needs to
	// happen
	protected Mob _mob;
	protected String _desc;
	protected int _duration;

	public Affect(Mob mob_, String desc_, int duration_) {
		_mob = mob_;
		_duration = duration_;
		_desc = desc_;
	}

	public void applyEffect() {
		// TODO remove mob from these classes.
		_mob.out("You feel the affects of " + _desc);

	}

	public String getDesc() {
		return _desc;
	}

	public boolean isExpired() {
		return (_duration < 1);
	}

	public void removeEffect() {
		_mob.out("The affects of " + _desc + " wear off");

	}

	public void tick() {
		_duration--;
		// poison would -1 to health or more...
	}

	@Override
	public String toString() {
		int index = _duration / 10;
		if (index > affDuration.length - 1) {
			index = affDuration.length - 1;
		}

		return _desc + " " + affDuration[index] + " (" + _duration + ")";
	}

	public int getBuff() {
		return 15;
	}
}
