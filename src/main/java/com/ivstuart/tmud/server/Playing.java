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

package com.ivstuart.tmud.server;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.command.admin.AdminCommand;
import com.ivstuart.tmud.common.MobState;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Playing implements Readable {

    private static final Logger LOGGER = LogManager.getLogger();

    private final Player player;
    private final Mob mob;
    private String previousCommandLine;

    /**
     *
     */
    public Playing(Player player) {
        this.player = player;
        mob = player.getMob();
    }

    /*
     * (non-Javadoc)
     *
     * @see server.Read#read(java.lang.String)
     */
    @Override
    public void read(String line) {

        if (line.equals("!")) {
            line = previousCommandLine;
        }

        line = player.applyAlias(line);

        LOGGER.debug("Line after alias is: " + line);

        if (player.getSnooper() != null) {
            player.getSnooper().out("You snoop:" + line);
        }

        String[] input = line.split(" ", 2);
        String parameters = "";

        // This is required as some commands act on their input
        if (input.length == 2) {
            parameters = input[1];
        }

        if (mob.getMobStatus().isFrozen()) {
            mob.out("You can only quit while frozen");
            if (!input[0].equals("quit")) {
                return;
            }
        }

        try {
            Command command = CommandProvider.getCommandByString(input[0]);

            MobState minState = command.getMinimumPosition();

            MobState currentState = mob.getState();

            if (minState != null && currentState.lessThan(minState)) {
                mob.out("You need to be at least " + minState.toString().toLowerCase() + " in order to " + command.getClass().getSimpleName().toLowerCase());
                return;
            }

            if (mob.getPlayer().getPossess() != null && !(command instanceof AdminCommand)) {
                command.execute(mob.getPlayer().getPossess(), parameters);
            } else {
                command.execute(mob, parameters);
            }

        } catch (Exception e) {
            LOGGER.error("Problem sourcing command for [ " + input[0] + " ]", e);
            mob.out(e.getMessage());
        }

        previousCommandLine = line;
    }
}
