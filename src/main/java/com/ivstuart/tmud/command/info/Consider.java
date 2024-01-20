/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Consider extends BaseCommand {

    public static final String[] compare = {
            "Now where did that chicken go?",
            "You could do it with a needle!",
            "Easy.",
            "The perfect match!",
            "You would need some luck!",
            "You would need a lot of luck and great equipment!",
            "Do you feel lucky, punk?",
            "Are you mad!?",
            "You ARE mad!"
    };

    @Override
    public void execute(Mob mob, String input) {

        Mob target = mob.getRoom().getMob(input);

        if (target == null) {
            mob.out(input + " is not here to consider!");
            return;
        }

        int index1 = 4 * target.getHp().getValue() / mob.getHp().getValue();

        int index2 = 4 * target.getMobLevel() / mob.getPlayer().getData().getLevel();

        int index3 = 4;

        if (target.getWeapon() != null) {
            index3 *= target.getWeapon().getDamage().getMaxRoll();
        }

        if (mob.getWeapon() != null) {
            index3 /= mob.getWeapon().getDamage().getMaxRoll();
        } else {
            index3 /= 6;
        }

        int index4 = 4 * (1 + target.getArmour()) / (1 + mob.getEquipment().getTotalArmour().getAverage());

        int compareIndex = (index1 + index2) / 2;

        if (compareIndex >= compare.length) {
            compareIndex = compare.length - 1;
        }

        int compareIndex2 = (index3 + index4) / 2;

        if (compareIndex2 >= compare.length) {
            compareIndex2 = compare.length - 1;
        }

        // play test this to see which states are best to determine if it is a fair fight or not.
        mob.out(compare[compareIndex]);
        mob.out(compare[compareIndex2]);

    }

}
