/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.Spell;
import com.ivstuart.tmud.world.World;

/**
 * Created by Ivan on 09/08/2016.
 */
public class Recall implements SpellEffect {

    public void effect(Mob caster, Mob target, Spell spell, Item targetItem) {

        if (caster.getFight().isEngaged()) {
            caster.out("You word of recall fizzles uselessly can not be used in combat");
            return;
        }

        Room toRoom = World.getPortal(caster);

        if (toRoom != null) {
            caster.out("You word of recall to your portal");
            caster.getRoom().remove(caster);
            toRoom.add(caster);
        } else {
            caster.out("Portal room not found! Please report bug to admin on bug channel");
        }
    }

    public boolean isPositiveEffect() {
        return true;
    }
}
