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

package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.Info;
import com.ivstuart.tmud.state.player.Attributes;
import com.ivstuart.tmud.state.mobs.Mob;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttributeInfo extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        mob.out(Info.BAR);

        Attributes att = mob.getPlayer().getAttributes();

        mob.out(att.getSTR().getDescription());
        mob.out(att.getCON().getDescription());
        mob.out(att.getDEX().getDescription());
        mob.out(att.getINT().getDescription());
        mob.out(att.getWIS().getDescription());

        mob.out(Info.BAR);

    }

}
