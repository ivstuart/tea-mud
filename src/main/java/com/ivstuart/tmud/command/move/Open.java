/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 12-Nov-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.move;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.constants.DoorState;
import com.ivstuart.tmud.state.*;

public class Open extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		Item item = mob.getRoom().getInventory().get(input);

		if (item != null) {
			openItem(mob, item);
			return;
		}

		Exit exit = mob.getRoom().getExit(input);

		if (exit == null) {
			mob.out("No visable exit in direction " + input);
			return;
		}

		Door door = exit.getDoor();

		if (door == null) {
			mob.out("No visable door in direction " + input);
			return;
		}

		if (door.getState() == DoorState.OPEN) {
			mob.out("Door is already open!");
			return;
		}

		if (door.getState() == DoorState.LOCKED) {
			mob.out("Door is locked!");
			return;
		}

		door.setState(DoorState.OPEN);

		mob.out("You open a door");
	}

	private void openItem(Mob mob, Item item) {

		if (!(item instanceof Chest)) {
			mob.out("That item can not be opened");
			return;
		}

		Chest chest = (Chest) item;

		if (chest.getState() == DoorState.OPEN) {
			mob.out("That item is already open");
			return;
		}

		if (chest.getState() == DoorState.LOCKED) {
			mob.out("That item is locked and will not open");
			return;
		}

		mob.out("You open a " + chest.getBrief());

		chest.setState(DoorState.OPEN);

	}

}
