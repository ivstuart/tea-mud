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
import com.ivstuart.tmud.state.Attribute;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Say extends BaseCommand {

    // say | shout | holler <string>

    @Override
    public void execute(Mob mob, String input) {

        Attribute drunk = mob.getPlayer().getData().getDrunkAttribute();
        if (drunk.getValue() > 100) {
            mob.out("You are soo drunk you slur your words.");
            input = input.replaceAll(" ", "rr ");
        }

        mob.getRoom().out(mob.getId() + " says, \"" + input + "\"");
    }

}
