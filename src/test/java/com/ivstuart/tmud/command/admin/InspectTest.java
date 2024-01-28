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

/**
 * Created by Ivan on 30/08/2016.
 */
public class InspectTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    public void testInspect() {

        Mob mob = TestHelper.makeDefaultPlayerMob("ted");
        mob.getPlayer().setAdmin(true);

        mob.getPlayer().setCreated(mob);

        Inspect inspect = new Inspect();
        inspect.execute(mob, null);

    }
}
