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
package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.ability.Cast;
import com.ivstuart.tmud.constants.SkillNames;
import com.ivstuart.tmud.state.*;
import com.ivstuart.tmud.world.World;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Zap extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        Item item = (Item) mob.getEquipment().getPrimary();

        if (!(item instanceof Wand)) {
            mob.out("You need to be carrying a rod or wand in order to zap");
            return;
        }

        Ability ability = mob.getLearned().getAbility(SkillNames.WANDS);

        if (ability.isNull()) {
            mob.out("You have no skill with wands or rods");
            return;
        }

        Wand wand = (Wand) item;

        if (wand.getCharges() < 1) {
            mob.out("You are out of charges to cast with this wand");
            return;
        }

        wand.useCharge();

        Spell spell = World.getSpell(wand.getProperties());

        if (spell == null) {
            mob.out("The spell " + wand.getProperties() + " is gibberish");
            return;
        }

        new Cast().execute(mob, spell, ability, spell.getName() + " " + input, false);

    }

}
