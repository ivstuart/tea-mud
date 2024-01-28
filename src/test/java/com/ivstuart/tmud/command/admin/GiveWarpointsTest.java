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
import com.ivstuart.tmud.utils.TestHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Ivan on 30/08/2016.
 */
public class GiveWarpointsTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    public void testGiveWarPoints() {

        Mob mob = TestHelper.makeDefaultPlayerMob("ted");
        mob.getPlayer().setAdmin(true);

        GiveWarpoints giveWarpoints = new GiveWarpoints();
        giveWarpoints.execute(mob, "1000");

        assertEquals("Has 1000 wps", 1000, mob.getPlayer().getData().getWarpoints());
    }
}
