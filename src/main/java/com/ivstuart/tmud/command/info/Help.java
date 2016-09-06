/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.state.Mob;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Help extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        // Have a help
        if (input.length() == 0) {
            showDefaultHelp(mob);
            return;
        }

        if ("commands".equals(input)) {
            showCommands(mob);
            return;
        }

        if ("skills".equals(input)) {
            CommandProvider.getCommand(HelpSkills.class).execute(mob, input);
            return;
        }

        if ("spells".equals(input)) {
            CommandProvider.getCommand(HelpSpells.class).execute(mob, input);
            return;
        }

        Command command = CommandProvider.getCommandByString(input);

        mob.out(command.getHelp());
    }

    private void showCommands(Mob mob) {
        Set<String> commandSet = new HashSet<String>();
        for (Command command : CommandProvider.getCommands()) {
            commandSet.add(command.getClass().getSimpleName());
        }

        Iterator<String> commandIter = commandSet.iterator();
        for (; commandIter.hasNext(); ) {
            mob.out(commandIter.next());
        }
    }

    private void showDefaultHelp(Mob mob) {
        String help = "help <topic|specific> \n" +
                "i.e. help commands\n" +
                "i.e. help skills\n" +
                "i.e. help spells\n" +
                "i.e. help bash\n" +
                "Also see HelpDamage HelpSkills HelpSpells";
        mob.out(help);

    }

}
