/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.command.ability.Cast;
import com.ivstuart.tmud.constants.SkillNames;
import com.ivstuart.tmud.world.World;

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

        Ability ability = mob.getLearned().getAbility(SkillNames.SCROLLS);

        if (ability.isNull()) {
            mob.out("You have no skill with scrolls");
            return;
        }

        new Cast().execute(mob, spell, ability, input_, false);
    }
}
