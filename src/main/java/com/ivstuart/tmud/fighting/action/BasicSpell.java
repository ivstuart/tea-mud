/*
 *  Copyright 2024. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.ivstuart.tmud.fighting.action;

import com.ivstuart.tmud.command.info.Prompt;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.state.mobs.Ability;
import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.places.Room;
import com.ivstuart.tmud.state.skills.Spell;
import com.ivstuart.tmud.utils.MudArrayList;

import java.util.List;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BasicSpell extends FightAction {

    public static final int FIVE_SECONDS = 5000; // Max time to cast

    private final Ability _ability;
    private final Spell _spell;

    private List<Mob> _targets;
    private Item _item;

    public BasicSpell(Ability ability_, Spell spell_, Mob me_, Mob target_) {
        super(me_, target_);
        _ability = ability_;
        _spell = spell_;
    }

    public BasicSpell(Ability ability_, Spell spell_, Mob me_, Item item_) {
        super(me_, me_);
        _ability = ability_;
        _spell = spell_;
        _item = item_;
    }

    public BasicSpell(Ability ability_, Spell spell_, Mob me_, Mob target_, List<Mob> targets_) {
        super(me_, target_);
        _ability = ability_;
        _spell = spell_;
        _targets = targets_;
    }

    /*
     * (non-Javadoc)
     *
     * @see fighting.FightAction#begin()
     */
    @Override
    public void begin() {

        // Test output string because at construction the fight action might be
        // queued for action at a later time.

//        if (checkCanCast() == false) {
//
//            getSelf().getMobStatus().setCasting(0);
//            this.destroy();
//            return;
//        }

        out(new Msg(getSelf(), getTarget(),
                "<S-You begin/NAME begins> to utter some strange incantations..."));

        // When a queued command resolves it need to work out if the target is
        // still visible.
        getSelf().getMobStatus().setCasting(6);


        super.begin();

        duration(5 / _spell.getSpeed());

    }

    /*
     * (non-Javadoc)
     *
     * @see fighting.FightAction#changed()
     */
    @Override
    public void changed() {

        out(new Msg(getSelf(), getTarget(),
                "<S-You are/NAME is> disrupted from attacking <T-you/NAME>."));


        // Consider setting CASTING to cast lagged when interrupted.
        getSelf().getMobStatus().setCasting(0);

    }

    private boolean checkCanCast() {
        if (!getMobMana().hasLevelToCast(_spell)) {
            out("You have lost sufficient ability to cast " + _spell.getId());
            return false;
        }

        if (!getMobMana().hasEnoughManaToCast(_spell)) {
            out("Your mana is too depleted to cast " + _spell.getId());
            return false;
        }

        getMobMana().cast(_spell);

        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see fighting.FightAction#ended()
     */
    @Override
    public void ended() {

        // out("TRACE ended called");

        duration(5);

        getSelf().getMobStatus().setCasting(0);
    }

    @Override
    public void happen() {

        // Start fight if aggressive action
        if (!isSpellEffectPositive()) {
            Fight.startCombat(getSelf(), getTarget());
        }

        Room room = getSelf().getRoom();

        MudArrayList<Mob> mobs = room.getMobs();

        // Can summon targets in other room locations.
        if (!_spell.isAnyTarget()) {
            if (mobs == null || !mobs.contains(getTarget())) {
                out(getTarget().getId() + " is no longer here you stop casting");

                getSelf().getMobStatus().setCasting(0);
                finished();
                return;
            }
        }

        out(new Msg(
                getSelf(),
                getTarget(),
                "<S-You launch/NAME launches> a bolt of energy from <S-your/GEN-his> fingertips directed at a <T-you/NAME>."));

        if (isSuccess() || isSpellEffectPositive()) {
            hit();
        } else {
            miss();
        }
        Prompt.show(getSelf());
        duration(1);
    }

    private boolean isSpellEffectPositive() {
        return _spell.getSpellEffect().isPositiveEffect();
    }

    private void hit() {

        // int amount = _spell.getDamage().roll();

        if (_targets != null) {
            for (Mob mob : _targets) {
                _spell.getSpellEffect().effect(getSelf(), mob, _spell, _item);
            }
        } else {
            _spell.getSpellEffect().effect(getSelf(), getTarget(), _spell, _item);
        }

        // Stat based damage modifier.

        // Fumbles. Critical.

    }

    /**
     * @return true for damage spells.
     */
    @Override
    public boolean isMeleeEnabled() {
        return (!_spell.getSpellEffect().isPositiveEffect());
    }

    /*
     */
    private boolean isSuccess() {
        return _ability.isSuccessful(this.getSelf());
    }

    /*
     * Some attacks which fail can have even more possibilities... i.e drop
     * weapon, hitting a friend etc... more fun.
     */
    private void miss() {
        out(new Msg(getSelf(), getTarget(),
                "<S-You/NAME> miss<S-/es> attacking <T-you/NAME>."));

        // out("You miss punching " + getTarget().getName());
    }

}
