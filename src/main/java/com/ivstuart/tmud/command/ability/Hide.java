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
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.constants.SkillNames;
import com.ivstuart.tmud.state.mobs.Ability;
import com.ivstuart.tmud.state.places.Exit;
import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.mobs.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Hide extends BaseCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * @param mob   player mob or mob
     * @param input calling code ensures that null is never passed in
     */
    @Override
    public void execute(Mob mob, String input) {


        if (mob.getFight().isEngaged() || mob.getFight().isFighting()) {
            mob.out("You can not hide while engaged in combat");
            return;
        }

        // hide objects
        // hide exits or props only if they are hidden in the first place and
        // made visible.

        // Success or fail
        Ability ability = mob.getLearned().getAbility(SkillNames.HIDE);

        if (ability == null || ability.isNull()) {
            mob.out("You have no knowledge of how to hide!");
            return;
        }

        if (!mob.getMv().deduct(10)) {
            mob.out("You do not have enough movement to hide.");
            return;
        }

        if (!input.isEmpty() && ability.isSuccessful(mob)) {
            executeHideObject(mob, input);
            return;
        }

        if (mob.isHidden()) {
            mob.out("You are already hidden.");
            return;
        }

        if (ability.isSuccessful(mob)) {
            mob.out(new Msg(mob, "<S-You/NAME> successfully hide."));
            mob.getMobStatus().setHidden(30);
            mob.setHidden(true);

        } else {
            mob.out(new Msg(mob, "<S-You/NAME> failed to hide."));
        }

        // Do "search" in collaboration with this command.

    }

    private void executeHideObject(Mob mob, String input) {
        // Try object on ground first
        // then exit

        Item item = mob.getRoom().getInventory().get(input);

        if (item != null) {
            item.setHidden(true);
            mob.getRoom().out(
                    "<S-You/NAME> hide " + item.getName() + " in this room");
            return;
        }

        LOGGER.debug("Item was null hence trying to hide a special exit instead");

        Exit exit = mob.getRoom().getExit(input);

        if (exit != null) {
            // Can only hide none N,S,E,W,UP,DOWN exits
            if (!exit.isHidden() && exit.isSpecial()) {
                exit.setHidden(true);
                mob.getRoom().out(
                        "<S-You/NAME> hide " + exit.getName()
                                + " to throw off pursuit!");
                return;
            }
        }

        Mob aMobToHide = mob.getRoom().getMob(input);

        if (aMobToHide != null) {
            mob.out("You hide mob " + aMobToHide.getName());
            aMobToHide.setHidden(true);
            return;
        }

        mob.out("You failing to find any " + input + " to hide");
    }

}
