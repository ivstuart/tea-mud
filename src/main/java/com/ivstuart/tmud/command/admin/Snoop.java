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

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.world.World;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Snoop extends AdminCommand {

    /**
     * View all input from player as if in the same room.
     */
    @Override
    public void execute(Mob mob, String input) {

        super.execute(mob, input);

        Player player = World.getPlayer(input);

        if (player == null) {
            mob.out("Can not find player " + input + " to snoop on");
            return;
        }

        if (input.equals("off")) {
            mob.out("You stop snooping on player " + player.getName());
            player.setSnooper(null);
        } else {
            mob.out("You start snooping on player " + player.getName() + " command with arg off to switch off snooping");
            player.setSnooper(mob);
        }
    }

}
