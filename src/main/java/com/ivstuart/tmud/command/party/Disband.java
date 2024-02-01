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

package com.ivstuart.tmud.command.party;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.mobs.Mob;

import java.util.Iterator;
import java.util.List;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Disband extends BaseCommand {

    /**
     *
     */
    public Disband() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob_, String input) {
        List<Mob> group = mob_.getPlayer().getGroup();

        if (group == null) {
            mob_.out("You have no group to disband");
        }

        if (input.equalsIgnoreCase("all")) {

            for (Mob mob : group) {
                mob.getPlayer().setGroup(null);
                mob.out("You are disbanded from your current group");
            }

            group.clear();
        }

        if (!input.isEmpty()) {
            Iterator<Mob> mobIterator = mob_.getPlayer().getGroup().iterator();

            while (mobIterator.hasNext()) {
                Mob mob = mobIterator.next();

                if (mob.getName().equals(input)) {
                    mobIterator.remove();
                    mob.getPlayer().setGroup(null);
                }
            }

        }


    }

}
