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
package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.utils.GsonIO;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Ban extends AdminCommand {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String fileName = "banned";
    private static List<String> bannedNames = null;

    public static boolean isBanned(String name) {
        return bannedNames.contains(name);
    }

    public static void init() {
        GsonIO io = new GsonIO();

        try {
            bannedNames = (List<String>) io.load(fileName, ArrayList.class);
        } catch (IOException e) {
            bannedNames = new ArrayList<>();
            LOGGER.error("IO problem ", e);
        }
    }

    @Override
    public void execute(Mob mob, String input) {

        super.execute(mob, input);

        String name = input;

        Player player = World.getPlayer(name);

        if (player != null) {
            player.out("You have been banned!");
            player.disconnect();
            name = player.getName();
        }

        if (bannedNames.contains(name)) {
            mob.out("Player already banned");
            return;
        }

        bannedNames.add(name);

        GsonIO io = new GsonIO();

        try {
            io.save(bannedNames, fileName);
        } catch (IOException e) {
            LOGGER.error("IO problem ", e);
        }

        // Ban <player Name> then bans all three fields.
        // Store data into properties file. name.ip name.email. name. or use
        // gson
        mob.out("Added to Ban file name " + name);

        // Ban a list of ip address, email address and or character names

    }

}
