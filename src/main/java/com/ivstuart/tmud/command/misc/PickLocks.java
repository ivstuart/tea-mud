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
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.misc;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.constants.DoorState;
import com.ivstuart.tmud.state.*;

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

        if (!mob.getInventory().hasLockpicks()) {
            mob.out("You have no lock picks available to do lock picking");
            return;
        }

        Ability ability = mob.getLearned().getAbility(PICK_LOCKS);

        if (ability.isNull()) {
            mob.out("You have no such ability to pick locks");
            return;
        }

        Item item = mob.getRoom().getInventory().get(input);

        if (item != null) {
            pickLockItem(mob, item, ability);
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

        if (DiceRoll.ONE_D100.rollLessThanOrEqualTo(5)) {
            mob.getInventory().getItems().remove("lockpicks");
            mob.out("You broken your lock picks on this lock, oh deer");
            return;
        }

        if (!DiceRoll.ONE_D100.rollLessThanOrEqualTo(door.getDifficulty())) {
            mob.out("You failed pick the lock");
            return;
        }

        if (ability.isSuccessful(mob)) {
            door.setState(DoorState.CLOSED);
            mob.out("You successfully pick the lock");
        } else {
            mob.out("You failed pick the lock");
        }

    }

    private void pickLockItem(Mob mob, Item item, Ability ability) {

        if (!(item instanceof Chest)) {
            mob.out("That item can not be picked");
            return;
        }

        Chest chest = (Chest) item;

        if (chest.getState() == DoorState.OPEN) {
            mob.out("That item is already open");
            return;
        }

        if (chest.getState() == DoorState.CLOSED) {
            mob.out("That item is already unlocked");
            return;
        }

        if (!chest.isPickable()) {
            mob.out("You can not pick this lock");
            return;
        }

        if (DiceRoll.ONE_D100.rollLessThanOrEqualTo(5)) {
            mob.getInventory().getItems().remove("lockpicks");
            mob.out("You broken your lock picks on this lock, oh deer");
            return;
        }

        if (!DiceRoll.ONE_D100.rollLessThanOrEqualTo(chest.getDifficulty())) {
            mob.out("You failed pick the lock");
            return;
        }

        if (ability.isSuccessful(mob)) {
            chest.setState(DoorState.CLOSED);
            mob.out("You pick the lock of a " + chest.getBrief());
        } else {
            mob.out("You failed pick the lock");
        }

    }


}
