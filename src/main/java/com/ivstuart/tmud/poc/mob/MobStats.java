package com.ivstuart.tmud.poc.mob;

import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.state.Attribute;

public class MobStats {

    // Prompt stats
    private Attribute health;
    private MobMana mana;
    private Attribute moves;

    // Fighting stats
    private int attacks;
    private int defensive;
    private int offensive;

    // Armour and Saves
    private int armour; // should be calculated on demand from equipment for location hit.

    private int[] saves;

}
