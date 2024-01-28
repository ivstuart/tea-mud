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
public class New extends AdminCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(Mob mob, String input) {

        if (input.isEmpty()) {
            mob.out("Format is: new state.Item");
            return;
        }

        Object obj = null;
        try {
            obj = StateReader.getInstance().createOrAddNewObject(null, input, null);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Class not found in builder", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Making new instance in builder", e);
        }

        if (obj == null) {
            return;
        }

        mob.out("Setting held object to " + obj);
        mob.getPlayer().setCreated(obj);

        String path = LaunchMud.mudServerProperties.getProperty("building.save.dir");
        FileHandle fileHandle = new FileHandle(path + "admin-" + mob.getName() + ".txt");

        try {
            fileHandle.write("class: " + input + "\n");
            fileHandle.close();
        } catch (IOException e) {
            LOGGER.error("Writing to building file", e);
        }
    }
}
