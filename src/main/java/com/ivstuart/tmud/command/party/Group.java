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
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.party;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.info.Prompt;
import com.ivstuart.tmud.state.Mob;

import java.util.List;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Group extends BaseCommand {

    /**
     *
     */
    public Group() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob, String input) {

        if (input.length() == 0) {
            showGroupInfo(mob);
            return;
        }

        if (input.equalsIgnoreCase("all")) {
            createNewGroup(mob);
            return;
        }

        // Try to add one player to existing group
        Mob aMob = mob.getRoom().getMob(input);

        if (!aMob.isPlayer()) {
            mob.out("Mob is not a player");
            return;
        }

        if (!aMob.isFollowing(mob)) {
            mob.out("Mob is not following you hence can not be grouped");
            return;
        }

        if (mob.getPlayer().getGroup() == null) {
            mob.out("You have no group to add this player with use group all");
            return;
        }

        if (mob.getPlayer().getGroup().contains(aMob)) {
            mob.out("Is already in your group");
            return;
        }

        mob.getPlayer().getGroup().add(aMob);

        mob.out("You add a player to your little group");
    }

    private void showGroupInfo(Mob mob_) {
        List<Mob> group = mob_.getPlayer().getGroup();

        if (group == null) {
            mob_.out("You are not currently part of any xp group");
            return;
        }

        mob_.out("$K~$J");

        for (Mob mob : group) {
            mob_.out("["+mob.getName()+"] "+ Prompt.getPrompt(mob));
        }

        mob_.out("$K~$J");
    }

    private void createNewGroup(Mob mob_) {

        List<Mob> group = mob_.getRoom().getFollowers(mob_);

        if(group.size() == 0) {
            mob_.out("No one else is following you to create a group with");
            return;
        }
        group.add(mob_); // Leader of the group.

        for (Mob mob : group) {
            mob.out("You are added into "+mob_.getName()+"'s group to xp in");
            mob.getPlayer().setGroup(group);
        }
    }

}
