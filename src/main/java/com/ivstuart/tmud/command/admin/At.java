/*
 *  Copyright 2024. Ivan Stuart
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

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.state.mobs.Mob;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class At extends AdminCommand {

    /**
     * At allows you to pass a command line to another player
     */
    @Override
    public void execute(Mob mob, String input) {

        super.execute(mob, input);

        // mob command args
        String[] args = input.split(" ", 3);

        if (args.length < 2) {
            mob.out("Usage: mob command args");
            return;
        }

        Mob target = mob.getRoom().getMob(args[0]);

        if (target == null) {
            mob.out(input + " is not here to at!");
            return;
        }

        mob.out("You make mob " + target.getName() + " " + args[1] + " with " + args[2]);
        CommandProvider.getCommandByString(args[1]).execute(target, args[2]);


    }

}
