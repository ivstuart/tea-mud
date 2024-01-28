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

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.world.World;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Teleport extends AdminCommand {

    /**
     * Teleport instantly to new target location
     */
    @Override
    public void execute(Mob mob, String input) {

        super.execute(mob, input);

        Room toRoom = World.getRoom(input);

        if (toRoom != null) {
            mob.out("You teleport to " + toRoom.getId());
            mob.getRoom().remove(mob);
            toRoom.add(mob);
        } else {
            mob.out("Room " + input + " not found!");
        }
    }

}
