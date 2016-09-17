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
public class SwapAlignment extends AdminCommand {

    /**
     * Instantely kill any mob
     */
    @Override
    public void execute(Mob mob, String input) {

        super.execute(mob, input);

        if (input.length() == 0) {
            mob.getPlayer().getData().getAlignment().invert();

            mob.out("You feel your alignment flip over");
            return;
        }

        Mob target = mob.getRoom().getMob(input);

        if (target == null) {
            mob.out(input + " is not here to smite!");
            return;
        }

        if (target.isPlayer()) {
            target.getPlayer().getData().getAlignment().invert();

            target.out("You feel your alignment flip over");
            return;
        }

        target.setAlignment(!target.isGood());

        mob.out("You feel " + mob.getName() + " alignment flip over");
    }

}
