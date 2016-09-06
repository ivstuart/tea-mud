/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 17-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command;

import com.ivstuart.tmud.common.MobState;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BaseCommand implements Command {

    private MobState position;

    /**
     * @param mob
     * @param input calling code ensures that null is never passed in
     */
    public void execute(Mob mob, String input) {
        mob.out("Base command does nothing");
    }

    public MobState getMinimumPosition() {
        return position;
    }

    public void setMinimumPosition(MobState state) {
        this.position = state;
    }

    @Override
    public String getHelp() {
        return null;
    }
}
