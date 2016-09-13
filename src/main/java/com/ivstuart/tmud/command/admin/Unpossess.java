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

import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Unpossess extends AdminCommand {

    /**
     * Make a target mob the mob you control
     * technical note to park your original mob somewhere safe in the meantime.
     */
    @Override
    public void execute(Mob mob, String input) {

        super.execute(mob, input);

        mob.out("You remove anyone you where possessing");
        mob.getPlayer().getPossess().setPossessed(null);
        mob.getPlayer().setPossess(null);

    }

}
