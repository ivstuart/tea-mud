/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.misc;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.constants.DoorState;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Door;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.constants.SkillNames.PICK_LOCKS;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PickLocks extends BaseCommand {


	@Override
	public void execute(Mob mob, String input) {

        if (!mob.getLearned().hasLearned("pick locks")) {
            mob.out("You have no knowledge of pick locks");
            return;
        }

        Exit exit = mob.getRoom().getExit(input);

        if (exit == null) {
            mob.out("There is no door in that direction");
            return;
        }

        Door door = exit.getDoor();

        if (door == null) {
            mob.out("There is no door in that way");
            return;
        }

        if (!door.isPickable() || door.getState() != DoorState.LOCKED) {
            mob.out("The door is not pick able check if it is locked");
            return;
        }
        Ability ability = mob.getLearned().getAbility(PICK_LOCKS);

        if (ability.isNull()) {
            mob.out("You have no such ability to pick locks");
            return;
        }

        if (ability.isSuccessful()) {
            door.setState(DoorState.CLOSED);
            mob.out("You successfully pick the lock");
        }

        if (ability.isImproved()) {
            mob.out("[[[[ Your ability to " + ability.getId()
                    + " has improved ]]]]");
            ability.improve();
        }
    }


}
