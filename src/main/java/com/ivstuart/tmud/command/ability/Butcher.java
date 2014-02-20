/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Food;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Butcher implements Command {

	/*
	 * 2ndary action?
	 */
	@Override
	public void execute(Mob mob, String input) {

		// TODO create command "salt" & "cook"
		// Need any weapon with sword, knife, edge, sharp, axe, blade in the short desc 
		// then also some animal meat - yields 3 portions of food that are perishable
		// salting meet makes it last 10 times longer. 
		// Fire can cook meat but 30% change of burning it.
		
		Item item = mob.getInventory().get(input);
		
		// TODO decide if this is on prop or items
		if (!item.isButcherable()) {
			mob.out(input+" is not editable animal skin, can not butcher it");
			return;
		}
		
		if (!mob.getInventory().hasSharpEdge()) {
			mob.out(input +" has no sharpe edge capable of being used to butcher the animal");
			return;
		}
		
		// Butcher animal
		
		mob.getInventory().remove(item);
		
		Food animalMeat = new Food();
		
		animalMeat.setNumberPortions(item.getWeight());
		animalMeat.setWeight(item.getWeight());
		animalMeat.setBrief("some animal meat");
		animalMeat.setId("meat");
		
		mob.getInventory().add(animalMeat);
	}

}
