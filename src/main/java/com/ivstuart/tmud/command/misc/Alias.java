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

package com.ivstuart.tmud.command.misc;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.mobs.Mob;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Alias extends BaseCommand {

    // Each player has their own alias mappings

    @Override
    public void execute(Mob mob, String input) {

        // mm cast magic missile
        if (input.isEmpty()) {
            mob.getPlayer().showAlias();
            return;
        }

        String[] words = input.split(" ", 2);

        if (words.length == 2) {
            mob.getPlayer().addAlias(words[0], words[1]);
            mob.out("Adding alias '" + words[0] + "' as '" + words[1] + "'");
        } else if (words.length == 1) {
            mob.getPlayer().removeAlias(words[0]);
            mob.out("Removing alias '" + words[0]);
        } else {
            mob.out("Nothing to alias");
        }

    }

}
