/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.misc.ForcedQuit;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DeletePlayer extends AdminCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(Mob mob, String input) {

        super.execute(mob, input);

        String name = input;

        Player player = World.getPlayer(input);

        if (player != null) {
            new ForcedQuit().execute(player.getMob(), null);
        }

        String path = LaunchMud.mudServerProperties.getProperty("player.save.dir");
        File file = new File(path + name.toLowerCase() + ".sav");
        if (!file.exists() || file.isDirectory()) {
            mob.out("There is no file " + file.getAbsolutePath());
            return;
        }

        mob.out("Removing player file " + file.getAbsolutePath());
        file.delete();


    }

}
