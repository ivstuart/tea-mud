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

public class Unlock extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {
		Item item = mob.getRoom().getInventory().get(input);

		if (item != null) {
			unlockItem(mob, item);
			return;
		}

		Exit exit = mob.getRoom().getExit(input);

		if (exit == null) {
			mob.out("No visible exit in direction " + input);
			return;
		}

		Door door = exit.getDoor();

		if (door == null) {
			mob.out("No visible door in direction " + input);
			return;
		}

		if (door.getState() == DoorState.OPEN) {
			mob.out("Door is open!");
			return;
		}

		if (door.getState() == DoorState.CLOSED) {
			mob.out("Door is already unlocked!");
			return;
		}

		if (!mob.getInventory().containsKey(door.getKeyId())) {
			mob.out("You do not have the key required to unlock door in direction "
					+ input);
			return;
		}

		door.setState(DoorState.CLOSED);

		mob.out("You unlock a door");
	}

	private void unlockItem(Mob mob, Item item) {

		if (!(item instanceof Chest)) {
			mob.out("That item can not be unlocked");
			return;
		}

		Chest chest = (Chest) item;

		if (chest.getState() == DoorState.OPEN) {
			mob.out("That item must be closed to unlock it");
			return;
		}

		if (chest.getState() == DoorState.CLOSED) {
			mob.out("That item is already unlocked");
			return;
		}

		if (!mob.getInventory().containsKey(chest.getKeyId())) {
			mob.out("You do not have the key required to unlock this");
			return;
		}

		mob.out("You unlock a " + chest.getBrief());

		chest.setState(DoorState.CLOSED);

	}
}
