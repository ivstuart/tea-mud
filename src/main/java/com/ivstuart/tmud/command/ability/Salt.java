/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Food;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Salt extends BaseCommand {

    /*
     * 2ndary action?
     */
    @Override
    public void execute(Mob mob, String input) {

        // Take salt from inventory and use it up.

        Item item = mob.getInventory().get(input);

        if (!(item instanceof Food)) {
            mob.out(input + " is not for salting");
            return;
        }

        Food food = (Food) item;

        if (!food.isSaltable()) {
            mob.out("Food is already full of salt");
            return;
        }

        food.setSaltable(false);
        int portions = food.getPortions();
        food.setPortions(portions++);

        // Also have salt meat

    }

}
