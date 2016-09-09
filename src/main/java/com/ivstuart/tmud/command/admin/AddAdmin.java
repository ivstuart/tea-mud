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

import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.utils.GsonIO;
import com.ivstuart.tmud.world.World;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AddAdmin extends AdminCommand {

    private static List<String> adminNames = null;


    private static String fileName = "admin";

    public static boolean isAdmin(String name) {
        return adminNames.contains(name);
    }

    public static void init() {
        GsonIO io = new GsonIO();

        try {
            adminNames = (List) io.load(fileName,
                    ArrayList.class);
        } catch (IOException e) {
            adminNames = new ArrayList<String>();
            e.printStackTrace();
        }
    }

    @Override
    public void execute(Mob mob, String input) {

        super.execute(mob, input);

        String name = input;

        String path = LaunchMud.mudServerProperties.getProperty("player.save.dir");
        File file = new File(path + name.toLowerCase() + ".sav");
        if (!file.exists() || file.isDirectory()) {
            mob.out("There is no file " + file.getAbsolutePath());
            return;
        }

        if (adminNames.contains(name)) {
            mob.out(name + " have been demoted from admin!");
            adminNames.remove(name);
            Mob aMob = World.getMob(name);

            if (aMob != null) {
                aMob.getPlayer().setAdmin(false);
            }
        } else {
            mob.out(name + " have been promoted to admin!");
            adminNames.add(name);
            Mob aMob = World.getMob(name);
            if (aMob != null) {
                aMob.getPlayer().setAdmin(true);
            }
        }

        GsonIO io = new GsonIO();

        try {
            io.save(adminNames, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
