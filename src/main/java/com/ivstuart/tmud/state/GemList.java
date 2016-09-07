/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.common.Equipable;

import java.util.ArrayList;
import java.util.List;

public class GemList extends Item implements Equipable {

    private static final long serialVersionUID = -5149906568548224750L;

    protected List<Gem> gems;

    public GemList() {
        gems = new ArrayList<>(4);
    }

    @Override
    public void equip(Mob mob) {
        for (Gem gem : gems) {
            gem.equip(mob);
        }
    }

    public void setLevel(String level_) {
        String element[] = level_.split(" ");
        int index = 0;
        for (String levelString : element) {
            Gem gem = gems.get(index);
            gem.setLevel(levelString);
            index++;
        }

    }

    public void setMana(String mana_) {
        String element[] = mana_.split(" ");
        for (String manaString : element) {
            Gem gem = new Gem();
            gems.add(gem);
            gem.setMana(manaString);
        }
    }

    @Override
    public void unequip(Mob mob) {
        for (Gem gem : gems) {
            gem.unequip(mob);
        }
    }

}
