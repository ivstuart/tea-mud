package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.Spell;
import com.ivstuart.tmud.state.World;

/**
 * Created by Ivan on 09/08/2016.
 */
public class Recall implements SpellEffect {

    public void effect(Mob caster, Mob target, Spell spell) {

        if (caster.getFight().isEngaged()) {
            caster.out("You world of recall fizzles uselessly can not be used in combat");
            return;
        }

        String input = "R-P1";// R-P2 need to check alignment for correct portal
        if (caster.getPlayer().getData().getAlignment().getValue() > 0) {
            input = "R-P2";
        }


        Room toRoom = World.getRoom(input);

        if (toRoom != null) {
            caster.out("You world of recall to " + toRoom.getId());
            caster.getRoom().remove(caster);
            toRoom.add(caster);
        } else {
            caster.out("Room " + input + " not found!");
        }
    }

    public boolean isPositiveEffect() {
        return true;
    }
}
