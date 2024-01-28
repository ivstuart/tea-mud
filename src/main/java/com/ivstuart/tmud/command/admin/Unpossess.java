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

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Unpossess extends AdminCommand {

    /**
     * Make a target mob the mob you control
     * technical note to park your original mob somewhere safe in the meantime.
     */
    @Override
    public void execute(Mob mob, String input) {

        super.execute(mob, input);

        mob.out("You remove anyone you where possessing");
        mob.getPlayer().getPossess().setPossessed(null);
        mob.getPlayer().setPossess(null);

    }

}
