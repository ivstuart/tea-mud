/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.*;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Butcher extends BaseCommand {

	/*
	 * 2ndary action?
	 */
	@Override
	public void execute(Mob mob, String input) {

		// Need any weapon with sword, knife, edge, sharp, axe, blade in the short desc 
		// then also some animal meat - yields 3 portions of food that are perishable
		// salting meet makes it last 10 times longer. 
		// Fire can cook meat but 30% change of burning it.

		Prop prop = mob.getRoom().getProps().get(input);

		Item item;
		boolean propFlag = false;
		if (prop == null || !(prop instanceof Item)) {
			item = mob.getInventory().get(input);
			propFlag = true;
		} else {
			item = (Item) prop;
		}

		if (!item.isButcherable()) {
			mob.out(input+" is not editable animal skin, can not butcher it");
			return;
		}
		
		if (!mob.getInventory().hasSharpEdge()) {
			mob.out(input +" has no sharpe edge capable of being used to butcher the animal");
			return;
		}
		
		// Butcher animal

		if (propFlag) {
			mob.getRoom().getProps().remove(item);
			if (item instanceof Corpse) {
				Corpse corpse = (Corpse) item;
				corpse.getInventory();
				mob.getRoom().getInventory().addAll(corpse.getInventory());
			}
		} else {
			mob.getInventory().remove(item);
		}
		
		Food animalMeat = new Food();
		
		animalMeat.setNumberPortions(item.getWeight());
		animalMeat.setWeight(item.getWeight());
		animalMeat.setBrief("some animal meat");
		animalMeat.setId("meat");
		
		mob.getInventory().add(animalMeat);
	}

}
