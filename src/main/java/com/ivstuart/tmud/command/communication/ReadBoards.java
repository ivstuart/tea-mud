/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.communication;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.Note;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.Boards;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ReadBoards extends BaseCommand {

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob, String input) {

        int alreadyReadIndex = mob.getPlayer().getReadIndex();

        if (input.length() > 0) {
            alreadyReadIndex = Integer.parseInt(input);
        }

        for (; ; ) {
            Note note = Boards.getNote(alreadyReadIndex);

            if (note == null) {
                mob.out("No more messages to read");
                break;
            }

            if (note.getPrivate() != null) {
                if (mob.getName().equalsIgnoreCase(note.getPrivate()) || mob.getName().equalsIgnoreCase(note.getAuthor())) {
                    mob.out(note.toString());
                    mob.getPlayer().incrementReadIndex();
                    break;
                }
            } else {
                mob.out(note.toString());
                mob.getPlayer().incrementReadIndex();
                break;
            }
            alreadyReadIndex++;
            mob.getPlayer().incrementReadIndex();
        }

    }

}
