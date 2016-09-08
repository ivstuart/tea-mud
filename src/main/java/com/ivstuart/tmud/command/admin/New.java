/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
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

        if (input.length() == 0) {
            mob.out("Format is: new state.Item");
            return;
        }

        Object obj = null;
        try {
            obj = StateReader.getInstance().createOrAddNewObject(null, input, null);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Making new instance in builder", e);
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
