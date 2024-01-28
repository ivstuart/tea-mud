/*
 *  Copyright 2024. Ivan Stuart
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

package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.constants.SkillNames.SEARCHING;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Search extends BaseCommand {

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob, String input) {

        Ability ability = mob.getLearned().getAbility(SEARCHING);

        if (ability == null || ability.isNull()) {
            mob.out("You have no knowledge of how to search!");
            return;
        }

        if (!mob.getMv().deduct(10)) {
            mob.out("You do not have enough movement to search.");
            return;
        }

        if (!ability.isSuccessful(mob)) {
            mob.out("Your search was unsuccessful");
            return;
        }

        boolean foundSomething = false;

        foundSomething = foundSomething || searchRoomForHiddenMobs(mob, ability);

        foundSomething = foundSomething || searchRoomForHiddenItems(mob);

        foundSomething = foundSomething || searchRoomForHiddenExits(mob);

        if (!foundSomething) {
            mob.out("Nothing here hidden to find");
        }


    }

    public boolean searchRoomForHiddenExits(Mob mob) {
        for (Exit exit : mob.getRoom().getExits()) {
            if (exit.isHidden()) {
                mob.out("You found some additional way out!");
                searchExit(exit);
                return true;
            }
        }
        return false;
    }

    private void searchExit(Exit exit) {
        exit.setHidden(false);

    }

    public boolean searchRoomForHiddenItems(Mob mob) {

        for (Item item : mob.getRoom().getInventory().getItems()) {
            if (item.isHidden()) {
                mob.out("You found something!");
                searchItem(item);
                return true;
            }
        }
        return false;
    }

    private void searchItem(Item item) {
        item.setHidden(false);
    }

    public boolean searchRoomForHiddenMobs(Mob mob, Ability ability) {
        for (Mob roomMob : mob.getRoom().getMobs()) {

            if (roomMob == mob) {
                continue;
            }

            if (roomMob.isHidden()) {
                mob.out("You found someone!");

                if (ability.isImproved()) {
                    mob.out("[[[[ Your ability to " + ability.getId()
                            + " has improved ]]]]");
                    ability.improve();
                }

                searchMob(roomMob);
                return true;
            }
        }
        return false;
    }

    private void searchMob(Mob roomMob) {
        roomMob.setHidden(false);
        roomMob.out("You have been found and are no longer hidden");
        roomMob.getMobStatus().setHidden(0);

    }

}
