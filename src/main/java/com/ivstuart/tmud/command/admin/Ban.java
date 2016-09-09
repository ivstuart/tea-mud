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
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Ban extends AdminCommand {
    private static final Logger LOGGER = LogManager.getLogger();
    private static List<String> bannedNames = null;


    private static String fileName = "banned";

    public static boolean isBanned(String name) {
        return bannedNames.contains(name);
    }

    public static void init() {
        GsonIO io = new GsonIO();

        try {
            bannedNames = (List) io.load(fileName, ArrayList.class);
        } catch (IOException e) {
            bannedNames = new ArrayList<String>();
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
