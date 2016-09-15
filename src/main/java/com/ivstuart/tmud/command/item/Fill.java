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
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Prop;
import com.ivstuart.tmud.state.Waterskin;
import com.ivstuart.tmud.utils.StringUtil;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Fill extends BaseCommand {

    /**
     * Fill waterskin from fountain
     * Fill waterskin fountain
     * Fill waterskin
     *
     * @param mob_
     * @param input_
     */
    @Override
	public void execute(Mob mob_, String input_) {

        String target = StringUtil.getLastWord(input_);
        // Check for fountain first of all
        Prop fountain = mob_.getRoom().getProps().get(target);

        if (fountain == null || !fountain.isWaterSource()) {
            mob_.out("That is not a water source");
            return;
        }

        String container = StringUtil.getFirstWord(input_);

        Item item = mob_.getInventory().get(container);

        if (item == null) {
            item = (Item) mob_.getEquipment().get(container);
        }

        if (item == null) {
            mob_.out("You are not carrying a " + container + " to fill.");
            return;
		}

		if (item instanceof Waterskin) {
			Waterskin waterskin = (Waterskin) item;

            mob_.out("You fill a " + waterskin.getLook());

			waterskin.fill();
		} else {
			mob_.out("The " + item.getLook() + " is not fillable.");
		}

	}

}
