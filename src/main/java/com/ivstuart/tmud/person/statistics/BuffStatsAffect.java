package com.ivstuart.tmud.person.statistics;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class BuffStatsAffect extends Affect {

	private final Spell spell;

	public BuffStatsAffect(Mob mob_, String desc_, int duration_) {
		super(mob_, desc_, duration_);
		this.spell = null;
	}

	public BuffStatsAffect(Mob target_, Spell spell) {
		super(target_,spell.getId(),spell.getDuration().roll());
		this.spell = spell;
	}

	@Override
	public void applyEffect() {
		_mob.out("You feel the affects of " + _desc);

		String stat = spell.getStat();
		if (stat == null) {
			_mob.out("You feel the affects of zero stat buff " + _desc);
			return;
		}
		int amount = spell.getDamage().roll();
		_mob.getPlayer().getAttributes().get(stat).savePoint();
		_mob.getPlayer().getAttributes().get(stat).increaseMaximum(amount);
		_mob.getPlayer().getAttributes().get(stat).increase(amount);
	}

	@Override
	public void removeEffect() {
		_mob.out("The affects of " + _desc + " wear off");
		String stat = spell.getStat();
		if (stat == null) {
			_mob.out("You feel the affects of zero stat buff wear off " + _desc);
			return;
		}
		_mob.getPlayer().getAttributes().get(stat).rollback();

	}

	public void setDuration(int duration) {
		this._duration = duration;
	}
}
