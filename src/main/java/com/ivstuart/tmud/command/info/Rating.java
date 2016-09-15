/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Armour;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.constants.SpellNames.*;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Rating extends BaseCommand {

    /**
     *
     */
    public Rating() {
        super();
    }

    public static int getRating(Mob mob) {
        int rating = mob.getPlayer().getData().getLevel();

        rating += mob.getPlayer().getAttributes().getTotal();

        if (mob.getMobAffects().hasAffect(SANCTURY)) {
            rating += 30;
        }
        if (mob.getMobAffects().hasAffect(BLUR)) {
            rating += 10;
        }
        if (mob.getMobAffects().hasAffect(STONE_SKIN)) {
            rating += 10;
        }
        if (mob.getMobAffects().hasAffect(BARRIER)) {
            rating += 10;
        }
        if (mob.getMobAffects().hasAffect(PROTECTION)) {
            rating += 10;
        }
        if (mob.getMobAffects().hasAffect(COMBAT_SENSE)) {
            rating += 10;
        }
        if (mob.getMobAffects().hasAffect(IMP_BLUR)) {
            rating += 100;
        }

        Armour armour = mob.getEquipment().getTotalArmour();
        rating += armour.getAverage();

        return rating;
    }

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob, String input) {

        int rating = getRating(mob);

        mob.out("Your current rating is " + rating);

    }

}
