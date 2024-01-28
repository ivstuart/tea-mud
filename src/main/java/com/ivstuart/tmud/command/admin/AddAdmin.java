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

import com.ivstuart.tmud.server.Login;
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
public class AddAdmin extends AdminCommand {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String fileName = "admin";
    private static List<String> adminNames = null;

    public static boolean isAdmin(String name) {
        return adminNames.contains(name);
    }

    public static void init() {

        if (adminNames != null) {
            return;
        }

        GsonIO io = new GsonIO();

        try {
            adminNames = (List<String>) io.load(fileName,
                    ArrayList.class);
        } catch (IOException e) {
            adminNames = new ArrayList<>();

            LOGGER.error("Problem initialising:" + e);

        }
    }

    @Override
    public void execute(Mob mob, String input) {

        super.execute(mob, input);

        if (!Login.checkFileExist(input)) {
            mob.out("There is no file");
            return;
        }

        if (adminNames.contains(input)) {
            mob.out(input + " have been demoted from admin!");
            adminNames.remove(input);
            Mob aMob = World.getMob(input);

            if (aMob != null) {
                aMob.getPlayer().setAdmin(false);
            }
        } else {
            mob.out(input + " have been promoted to admin!");
            adminNames.add(input);
            Mob aMob = World.getMob(input);
            if (aMob != null) {
                aMob.getPlayer().setAdmin(true);
            }
        }

        GsonIO io = new GsonIO();

        try {
            io.save(adminNames, fileName);
        } catch (IOException e) {
            LOGGER.error("Problem saving:" + e);
        }


    }

}
