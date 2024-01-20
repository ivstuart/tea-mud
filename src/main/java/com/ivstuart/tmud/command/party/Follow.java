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
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.party;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Follow extends BaseCommand {

    /**
     *
     */
    public Follow() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob, String input) {

        Mob toFollow = mob.getRoom().getMobs().get(input.toLowerCase());

        if (toFollow == null) {
            mob.out("No one to follow of the name " + input);
            return;
        }

        if (input.equalsIgnoreCase("me")) {
            toFollow = mob;
        }

        if (mob == toFollow) {
            mob.out("No point in following self, but you stop following anyone.");
            mob.setFollowing(null);
            return;
        }

        mob.setFollowing(toFollow);

        mob.out("You start following " + toFollow.getName());

    }

}
