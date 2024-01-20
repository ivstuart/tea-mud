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
package com.ivstuart.tmud.command.communication;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.utils.FileHandle;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Calendar;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Bug extends BaseCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     *
     */
    @Override
    public void execute(Mob mob, String input) {
        synchronized (Bug.class) {
            World.out("BUG:" + input, mob.isGood());
            String path = LaunchMud.mudServerProperties.getProperty("player.save.dir");
            FileHandle fh = new FileHandle(path + "bug-report.txt");
            Calendar c = Calendar.getInstance();

            try {
                fh.write(c.getTime() + "\n" + input + "\n");
                fh.close();
            } catch (IOException e) {
                LOGGER.error("Problem writing bug to bug report file", e);
            }
        }
    }

}
