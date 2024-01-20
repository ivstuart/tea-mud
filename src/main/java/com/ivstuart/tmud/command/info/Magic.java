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
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.constants.ManaType.*;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Magic extends BaseCommand {

    public Magic() {
        super();
    }

    @Override
    public void execute(Mob mob_, String input_) {

        MobMana mana = mob_.getMana();

        if (mana == null) {
            mob_.out("You have no magical power");
            return;
        }

        String sb = "$K~$J" +
                "\n$H   /---\\                    Cur   Max   Level" +
                "\n$H  /  |  \\   " + mana.get(FIRE).display() +
                "\n$H /__/ \\__\\  " + mana.get(EARTH).display() +
                "\n$H \\  \\ /  /  " + mana.get(WATER).display() +
                "\n$H  \\  |  /   " + mana.get(AIR).display() +
                "\n$H   \\---/" +
                "\n$K~$J";

        mob_.out(sb);
    }

}
