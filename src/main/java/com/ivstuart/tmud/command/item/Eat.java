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
package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.person.statistics.diseases.Disease;
import com.ivstuart.tmud.state.Attribute;
import com.ivstuart.tmud.state.Food;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Eat extends BaseCommand {

	@Override
	public void execute(Mob mob_, String input_) {

		Item item = mob_.getInventory().get(input_);

		/**
		 * if (item == null) { item = (Item)mob_.getEquipment().get(input_); }
		 */

		if (item == null) {
			mob_.out("You are not carrying a " + input_ + " to eat.");
			return;
		}

		if (item instanceof Food) {
			Food food = (Food) item;

			if (food.getPortions() == 0) {
				mob_.out("You cannot eat from this as it is empty");
				return;
			}

			Attribute hunger = mob_.getPlayer().getData().getHunger();

			int max = hunger.getMaximum();

			int current = hunger.getValue();

			if (current + 20 > max) {
				mob_.out("You are not hungery enough to eat");
				return;
			}

			hunger.increase(100);

			mob_.out("You eat some " + food);

			food.eat();

			checkFoodForDisease(mob_, food);

			if (food.getPortions() == 0) {
				mob_.getInventory().remove(food);
				mob_.out("Food is gone you ate it all of that!");
				// food object should be gc() able now.
			}
		} else {
			mob_.out("The " + item.getLook() + " is not drinkable.");
		}

	}

	private void checkFoodForDisease(Mob mob, Food food) {
		if (food.getDisease() == null) {
			return;
		}

		Disease disease = food.getDisease();
		if (DiceRoll.ONE_D100.rollLessThanOrEqualTo(disease.getInfectionRate())) {
			Disease infection = (Disease) disease.clone();
			infection.setMob(mob);
			infection.setDuration(disease.getInitialDuration());
			mob.getMobAffects().add(disease.getId(), infection);
		}

	}

}
