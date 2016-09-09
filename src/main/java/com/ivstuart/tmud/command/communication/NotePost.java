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
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.Boards;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class NotePost extends BaseCommand {

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob, String input) {

        if (mob.getPlayer().getNote().getSubject() == null) {
            mob.out("Your note must have a subject use NoteSubject to add one");
            return;
        }

        if (mob.getPlayer().getNote().getTopic() == null) {
            mob.out("Your note must have a topic use NoteTopic to add one");
            return;
        }

        if (mob.getPlayer().getNote().getBody() == null) {
            mob.out("Your note must have a body use NoteBody to add one");
            return;
        }

        mob.out("You post your notes");
        Boards.postNote(mob.getPlayer().getNote());

    }

}
