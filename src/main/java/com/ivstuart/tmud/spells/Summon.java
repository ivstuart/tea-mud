package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.Spell;
import com.ivstuart.tmud.state.World;

/**
 * Created by Ivan on 09/08/2016.
 */
public class Summon implements SpellEffect {

    public void effect(Mob caster, Mob target, Spell spell) {

        if (caster.getFight().isEngaged()) {
            caster.out("You summon fizzles uselessly can not be used in combat");
            return;
        }

        // check same alignment
        if (Math.signum(caster.getPlayer().getData().getAlignment().getValue()) !=
                Math.signum(target.getPlayer().getData().getAlignment().getValue()))
                {
                    caster.out("You may only summon players of your own alignment");
                    return;
        }


        Room toRoom = caster.getRoom();

        if (toRoom != null) {
            caster.out("You summon to " + toRoom.getId());
            target.getRoom().remove(target);
            toRoom.add(target);
        } else {
            target.out("Room not found!");
        }
    }

    public boolean isPositiveEffect() {
        return true;
    }
}
