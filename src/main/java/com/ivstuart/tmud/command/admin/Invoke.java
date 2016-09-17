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

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.util.StateReader;
import com.ivstuart.tmud.utils.FileHandle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by Ivan on 08/09/2016.
 */
public class Invoke extends AdminCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(Mob mob, String input) {

        if (input.length() == 0) {
            mob.out("Format is: id: R-001-001 same as the resource files.");
            return;
        }

        String[] element = input.split(":", 2);

        if (element.length != 2) {
            mob.out("Format is: id: R-001-001 same as the resource files.");
            return;
        }

        element[1] = element[1].substring(1);

        Object obj = mob.getPlayer().getCreated();

        mob.out("You invoke method " + StateReader.getInstance().getMethodName(element[0]) + " with parameter " + element[1]);
        StateReader.getInstance().invokeMethod(obj, obj.getClass().getCanonicalName(), element[0], element[1]);

        String path = LaunchMud.mudServerProperties.getProperty("building.save.dir");
        FileHandle fileHandle = new FileHandle(path + "admin-" + mob.getName() + ".txt");

        try {
            fileHandle.write(input + "\n");
            fileHandle.close();
        } catch (IOException e) {
            LOGGER.error("Writing to building file", e);
        }

    }

    public String getClassPrefix() {
        return LaunchMud.mudServerProperties.getProperty("class.prefix");
    }
}
