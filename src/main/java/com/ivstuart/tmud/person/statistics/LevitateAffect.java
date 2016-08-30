package com.ivstuart.tmud.person.statistics;

import com.ivstuart.tmud.common.MobState;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class LevitateAffect extends Affect {

    private final Spell spell;

    public LevitateAffect(Mob mob_, String desc_, int duration_) {
        super(mob_, desc_, duration_);
        this.spell = null;
    }

    public LevitateAffect(Mob target_, Spell spell) {
        super(target_, spell.getId(), spell.getDuration().roll());
        this.spell = spell;
    }

    @Override
    public void applyEffect() {
        _mob.out("You feel the affects of " + _desc);
    }

    @Override
    public void removeEffect() {
        _mob.out("The affects of " + _desc + " wear off");

        if (!_mob.getRace().isFly() && _mob.isFlying()) {
            _mob.out("You stop flying and stand as the spell wears off");
            _mob.setState(MobState.STAND);
        }
    }

    public void setDuration(int duration) {
        this._duration = duration;
    }
}
