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
package com.ivstuart.tmud.command.misc;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.ProfessionMaster;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Leave extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        ProfessionMaster professionMaster = mob.getRoom().getProfessionMaster();

        if (professionMaster == null) {
            mob.out("There is no one here to leave a profession with");
            return;
        }

        if (mob.getPlayer().getProfession() == null) {
            mob.out("You already have no profession");
            return;
        }

        if (!professionMaster.getProf().equals(mob.getPlayer().getProfession())) {
            mob.out("This is the wrong profession master to leave from");
            return;
        }

        mob.getPlayer().setProfession(null);


    }

}
