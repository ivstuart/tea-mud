package com.ivstuart.tmud.state;

/**
 * Created by Ivan on 13/08/2016.
 */
public class Scroll extends Item {

    @Override
    public boolean isRecitable() {
        return true;
    }

    public void recite(Mob mob) {
        mob.out("You recite a scroll");
    }
}
