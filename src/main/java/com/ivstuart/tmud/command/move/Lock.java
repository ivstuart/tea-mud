/*
 * Created on 12-Nov-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.move;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.constants.DoorState;
import com.ivstuart.tmud.state.Door;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Mob;

public class Lock implements Command {

	@Override
	public void execute(Mob mob, String input) {

		Exit exit = mob.getRoom().getExit(input);

		if (exit == null) {
			mob.out("No visiable exit in direction " + input);
			return;
		}

		Door door = exit.getDoor();

		if (door == null) {
			mob.out("No visiable door in direction " + input);
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

}
