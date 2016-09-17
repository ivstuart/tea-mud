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

import com.ivstuart.tmud.command.misc.ForcedQuit;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DeletePlayer extends AdminCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(Mob mob, String input) {

        super.execute(mob, input);

        String name = input;

        Player player = World.getPlayer(input);

        if (player != null) {
            new ForcedQuit().execute(player.getMob(), null);
        }

        String path = LaunchMud.mudServerProperties.getProperty("player.save.dir");
        File file = new File(path + name.toLowerCase() + ".sav");
        if (!file.exists() || file.isDirectory()) {
            mob.out("There is no file " + file.getAbsolutePath());
            return;
        }

        mob.out("Removing player file " + file.getAbsolutePath());
        file.delete();


    }

}
