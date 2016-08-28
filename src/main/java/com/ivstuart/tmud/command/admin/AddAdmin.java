/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.World;
import com.ivstuart.tmud.utils.GsonIO;

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

    @Override
    public void execute(Mob mob, String input) {

        super.execute(mob, input);

        String name = input;

        // TODO check for a sav file instead
        Player player = World.getPlayer(name.toLowerCase());

        if (player != null) {

            if (player.isAdmin()) {
                player.out("You have been demoted from admin!");
                adminNames.remove(name);
                player.setAdmin(false);
            } else {
                player.out("You have been promoted to admin!");
                adminNames.add(name);
                player.setAdmin(true);
            }
        } else {
            mob.out("No player found of the name" + name);
        }


        GsonIO io = new GsonIO();

        try {
            io.save(adminNames, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


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

}
