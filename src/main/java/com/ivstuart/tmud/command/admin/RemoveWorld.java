/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Ivan on 08/09/2016.
 */
public class RemoveWorld extends AdminCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(Mob mob, String input) {

        Object object = World.remove(input);

        mob.out("You remove " + object + " from the world and store it");

        mob.getPlayer().setCreated(object);

    }
}
