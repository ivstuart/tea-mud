/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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

public class Lock extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		Item item = mob.getRoom().getInventory().get(input);

		if (item != null) {
			lockItem(mob, item);
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

		if (!door.isLockable()) {
			mob.out("This door has no lock in direction " + input);
			return;
		}

		if (door.getState() == DoorState.OPEN) {
			mob.out("Door is open!");
			return;
		}

		if (door.getState() == DoorState.LOCKED) {
			mob.out("Door is already locked!");
			return;
		}

		if (!mob.getInventory().containsKey(door.getKeyId())) {
			mob.out("You do not have the key required to lock door in direction "
					+ input);
			return;
		}

		door.setState(DoorState.LOCKED);

		mob.out("You lock a door");
	}

	private void lockItem(Mob mob, Item item) {

		if (!(item instanceof Chest)) {
			mob.out("That item can not be locked");
			return;
		}

		Chest chest = (Chest) item;

		if (chest.getState() == DoorState.OPEN) {
			mob.out("That item must be closed to lock it");
			return;
		}

		if (chest.getState() == DoorState.LOCKED) {
			mob.out("That item is already locked and closed");
			return;
		}

		if (!mob.getInventory().containsKey(chest.getKeyId())) {
			mob.out("You do not have the key required to lock this");
			return;
		}

		mob.out("You lock a " + chest.getBrief());

		chest.setState(DoorState.LOCKED);

	}

}
