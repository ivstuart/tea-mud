/*
 * Created on 24-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.fighting.action;

import com.ivstuart.tmud.command.info.Prompt;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.state.*;
import com.ivstuart.tmud.utils.MudArrayList;

import java.util.List;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BasicSpell extends FightAction {

    public static final int FIVE_SECONDS = 5000; // Max time to cast

    private Ability _ability;
    private Spell _spell;

    private List<Mob> _targets;
    private Item _item;

    public BasicSpell(Ability ability_, Spell spell_, Mob me_, Mob target_) {
        super(me_, target_);
        _ability = ability_;
        _spell = spell_;
    }

    public BasicSpell(Ability ability_, Spell spell_, Mob me_, Item item_) {
        super(me_, me_); // TODO maybe target should be null here.
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

        if (checkCanCast() == false) {

            getSelf().getMobStatus().setCasting(0);
            this.destory();
            return;
        }

        out(new Msg(getSelf(), getTarget(),
                "<S-You begin/NAME begins> to utter some strange incantations..."));

        // When a queued command resolves it need to work out if the target is
        // still visble.
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


        // TODO Need to switch from CASTING to cast lagged?
        getSelf().getMobStatus().setCasting(0);
        // getSelf().getMobStatus().setCastingLag();?

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

    private int clipRange(int percentage) {
        if (percentage < 5) {
            percentage = 5;
        }
        if (percentage > 95) {
            percentage = 95;
        }
        return percentage;
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
            // TODO initialise combat from here.
        }

        if (_ability.isImproved()) {
            getSelf().out(
                    ">>>>> [You have become better at " + _spell.getId()
                            + "!] <<<<<");
            _ability.improve();
        }

        Room room = getSelf().getRoom();

        MudArrayList mobs = room.getMobs();

        // Can summon targets in other room locations.
        if (!_spell.isAnyTarget()) {
            if (mobs == null || mobs.contains(getTarget()) == false) {
                out(getTarget().getId() + " is no longer here you stop casting");

                getSelf().getMobStatus().setCasting(0);
                finished();
                return;
            }
        }

        // ie if a snake is not fighting it will attack me back at this point.
        out(new Msg(
                getSelf(),
                getTarget(),
                "<S-You launch/NAME launches> a bolt of energy from <S-your/GEN-his> fingertips directed at a <T-you/NAME>."));

        // if (isSuccess(getFighter().getFight().getHitChance())) {
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
        // TODO spellEffect.apply() check resists()

        // Dodging?

        // APB - default 3 multiplier or other spell skills and affects

        // Stat based damage modifier.

        // Fumbles. Criticals.

        // Hit location

        // Take off armour

        // Saves

        // Could just send this object the FightAction to damage.

    }

    @Override
    public boolean isMeleeEnabled() {
        // return true for damage spells.
        // return (_spell.getDamage().getMaxRoll() > 0); cant use damage as also used for duration.
        return (!_spell.getSpellEffect().isPositiveEffect());
    }

    /*
     */
    private boolean isSuccess() {

        int chance = this.clipRange(_ability.getSkill());

        if ((Math.random() * 100) < chance) {
            return true;
        }
        return false;
    }

    /*
     * Some attacks which fail can have even more possiblities... i.e drop
     * weapon, hitting a friend etc... more fun.
     */
    private void miss() {
        out(new Msg(getSelf(), getTarget(),
                "<S-You/NAME> miss<S-/es> attacking <T-you/NAME>."));

        // out("You miss punching " + getTarget().getName());
    }

}
