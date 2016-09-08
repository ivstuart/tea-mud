/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

public class Potion extends Item {


    public void drink(Mob mob) {

        Spell spell = World.getSpell(this.getProperties());

        if (spell == null) {
            mob.out("The potion " + this.getProperties() + " has no effect");
            return;
        }

        spell.getSpellEffect().effect(mob, mob, spell, null);

    }
}
