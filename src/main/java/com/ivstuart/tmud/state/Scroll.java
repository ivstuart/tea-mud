/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.fighting.action.BasicSpell;
import com.ivstuart.tmud.fighting.action.FightAction;

import static com.ivstuart.tmud.utils.StringUtil.getLastWord;

/**
 * Created by Ivan on 13/08/2016.
 */
public class Scroll extends Item {

    @Override
    public boolean isRecitable() {
        return true;
    }

    public void recite(Mob mob, String input_) {

        mob.out("You recite a scroll");

        Spell spell = World.getSpell(this.getProperties());

        if (spell == null) {
            mob.out("The spell " + this.getProperties() + " is gibberish");
            return;
        }

        // TODO call Cast.execute(mob,spell,input,false) need to refactor Cast first.

        // Same as Cast without the mana cost
        if (mob.getRoom().isPeaceful() && !spell.getSpellEffect().isPositiveEffect()) {
            mob.out("You can not use offensive magic in this room");
            return;
        }

        String target = getLastWord(input_);

        Mob targetMob = mob.getRoom().getMobs().get(target);

        if (targetMob == null) {
            if (spell.getSpellEffect().isPositiveEffect()) {
                targetMob = mob;
            } else {
                targetMob = mob.getFight().getTarget();
            }
        }

        if (targetMob == null) {
            mob.out("No target for the spell it fizzles");
            return;
        }

        mob.out("You start casting " + spell.getId());

        FightAction spellFightAction = new BasicSpell(Ability.NULL_ABILITY, spell,
                mob, targetMob);

        mob.getFight().add(spellFightAction);


    }
}
