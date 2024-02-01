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

package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.world.World;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Users extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {


        String line = String.format(" %1$-3s %2$-5s %3$-16s %4$-16s %5$-4s %6$-8s %7$-16s",
                "Num",
                "Class",
                "Name",
                "State",
                "Idle",
                "Local Address",
                "Remote Address");
        mob.out(line);
        mob.out("$K~$J");

        int counter = 0;
        for (String playerName : World.getPlayers()) {
            counter++;
            Player player = World.getMob(playerName).getPlayer();

            if (player.getConnection() == null) {
                continue;
            }

            // String line = playerName + player.getData().getTitle();
            line = String.format(" %1$-3s %2$-5s %3$-16s %4$-16s %5$-4s %6$-8s %7$-16s",
                    counter,
                    player.getProfession(),
                    player.getName(),
                    player.getMob().getState(),
                    player.getConnection().getIdle() / 1000,
                    player.getConnection().getLocalAddress(),
                    player.getConnection().getRemoteAddress());

            mob.out(line);
        }

        mob.out("$K~$J");
    }

}
