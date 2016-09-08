/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.util.StateReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Ivan on 08/09/2016.
 */
public class Load extends AdminCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(Mob mob, String input) {

        LOGGER.info("Loader executed for Mob " + mob.getName() + " input " + input);
        try {
            StateReader.getInstance().load(input);
        } catch (Exception e) {
            LOGGER.error("Problem loading file " + input, e);
        }

    }
}
