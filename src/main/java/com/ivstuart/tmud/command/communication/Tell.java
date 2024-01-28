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

package com.ivstuart.tmud.command.communication;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.World;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Tell extends BaseCommand {

    /**
     * tell | whisper | ask <player> <string>
     */
    public Tell() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob, String input) {
        String[] inputSplit = input.split(" ", 2);

        if (inputSplit.length < 2) {
            mob.out("need source and target group to tell");
            return;
        }

        String message = "$D" + mob.getId() + " tells you -->" + inputSplit[1]
                + "$J";

        String name = inputSplit[0];

        if (!World.isOnline(name)) {
            mob.out("Tell who?");
            return;
        }

        Mob playerMob = World.getMob(name.toLowerCase());

        playerMob.out(message);
        mob.out("You:" + message);

        playerMob.setLastToldBy(mob);

    }
}
