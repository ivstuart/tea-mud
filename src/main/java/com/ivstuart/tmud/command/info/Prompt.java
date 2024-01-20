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
 * Created on 12-Oct-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.constants.FightConstants;
import com.ivstuart.tmud.person.statistics.ManaAttribute;
import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.state.Attribute;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.common.Colour.*;
import static com.ivstuart.tmud.constants.ManaType.*;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Prompt extends BaseCommand {

    private static String getLifeStatus(Mob target) {

        Attribute oppHp = target.getHp();

        int index = oppHp.getValue() * (FightConstants.healthStatus.length - 1)
                / oppHp.getMaximum();
        if (index < 0) {
            index = 0;
        }
        if (index >= FightConstants.healthStatus.length) {
            index = FightConstants.healthStatus.length - 1;
        }
        return FightConstants.healthStatus[index];
    }

    public static String getPrompt(Mob mob) {

        if (!mob.isPlayer()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        sb.append(mob.getMobStatus().getPrompt());

        // (Casting)
        // [+] Casting lagged

        // (Groundfighting)
        // [3] (Immobile)

        // [*] flurry lag

        MobMana mana = mob.getMana();

        if (mana == null) {
            mob.out("You have no magical power");
            return "";
        }

        Attribute hp = mob.getHp();
        Attribute mv = mob.getMv();

        ManaAttribute fm = mana.get(FIRE);
        ManaAttribute em = mana.get(EARTH);
        ManaAttribute wm = mana.get(WATER);
        ManaAttribute am = mana.get(AIR);

        sb.append(GREEN).append(hp.getPrompt()).append("Hp ");
        sb.append(BLUE).append(mv.getPrompt()).append("Mv ");

        if (mob.isRiding()) {
            sb.append(BLUE).append(mob.getMount().getMv().getPrompt()).append("Mt ");
        }

        sb.append(" $H<$I");

        sb.append(RED).append(fm.getPrompt()).append("Fi ");
        sb.append(BROWN).append(em.getPrompt()).append("Ea ");
        sb.append(BLUE).append(wm.getPrompt()).append("Wa ");
        sb.append(YELLOW).append(am.getPrompt()).append("Ai ");

        sb.append("$H>$J");

        // Kgs:
        sb.append("Kgs:0.0 ");

        // ( xp )
        sb.append("(");

        sb.append(mob.getPlayer().getData().getToLevelXp());

        sb.append(")");

        if (mob.getFight() != null) {
            Mob target = mob.getFight().getTarget();
            if (target != null) {

                sb.append(" [Op: ");
                sb.append(getLifeStatus(target));

                sb.append(target.getMobStatus().getPrompt());
                sb.append("]");
            }
        }

        return sb.toString();

    }

    public static void show(Mob self) {
        self.out(Prompt.getPrompt(self));
    }

    @Override
    public void execute(Mob mob_, String input_) {

        mob_.out(Prompt.getPrompt(mob_));
    }
}
