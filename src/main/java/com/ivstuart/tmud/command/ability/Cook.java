/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.state.Food;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Cook extends BaseCommand {

    /*
     * 2ndary action?
     */
    @Override
    public void execute(Mob mob, String input) {

        if (!mob.getRoom().hasFire()) {
            mob.out("No camping fire to cook on");
            return;
        }

        Item item = mob.getInventory().get(input);

        if (!(item instanceof Food)) {
            mob.out(input + " is not for cooking");
            return;
        }

        Food food = (Food) item;

        if (!food.isCookable()) {
            mob.out(input + " is not for the cooking pot");
            return;
        }

        if (DiceRoll.ONE_D100.rollLessThanOrEqualTo(30)) {
            mob.out("You burn the meat by over cooking it");
            food.setPortions(0);
            food.setLook("Which has been burned");
            return;
        }

        int weight = food.getWeight();
        food.setWeight((int) (weight * 0.9));
        int portions = food.getPortions();
        food.setPortions(portions++);

        // Also have salt meat

    }

}