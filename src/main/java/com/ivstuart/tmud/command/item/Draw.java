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
package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Draw extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        if (mob.getEquipment().hasBothHandsFull()) {
            mob.out("Both your hands are in use carrying items");
            return;
        }

        if (mob.getEquipment().draw(input)) {
            mob.out("You draw your weapon");
        } else {
            mob.out("You need your hands free to be able to draw");
        }

    }

}
