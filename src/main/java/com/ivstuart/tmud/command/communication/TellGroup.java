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
import com.ivstuart.tmud.person.config.ChannelEnum;
import com.ivstuart.tmud.state.mobs.Mob;

import java.util.List;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TellGroup extends BaseCommand {


    public TellGroup() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob_, String input) {

        List<Mob> group = mob_.getPlayer().getGroup();

        if (group == null) {
            mob_.out("You are not currently part of any xp group");
            return;
        }

        for (Mob mob : group) {

            if (mob.getPlayer().getConfig().getChannelData().isFlagSet(ChannelEnum.GROUP)) {
                mob.out("<" + mob_.getName() + "> say's " + input);
            }
        }

    }
}
