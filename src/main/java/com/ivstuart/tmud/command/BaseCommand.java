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

package com.ivstuart.tmud.command;

import com.ivstuart.tmud.common.MobState;
import com.ivstuart.tmud.state.mobs.Mob;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BaseCommand implements Command {

    private MobState position;

    /**
     * @param mob
     * @param input calling code ensures that null is never passed in
     */
    public void execute(Mob mob, String input) {
        mob.out("Base command does nothing");
    }

    public MobState getMinimumPosition() {
        return position;
    }

    public void setMinimumPosition(MobState state) {
        this.position = state;
    }

    @Override
    public String getHelp() {
        return null;
    }
}
