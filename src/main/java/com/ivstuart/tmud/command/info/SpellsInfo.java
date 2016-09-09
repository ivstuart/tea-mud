/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.Info;
import com.ivstuart.tmud.constants.AbilityConstants;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;
import com.ivstuart.tmud.world.World;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SpellsInfo extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        StringBuilder sb = new StringBuilder();

        int playerLevel = mob.getPlayer().getData().getLevel();

        sb.append(Info.BAR2).append("\n");

        for (Ability ab : mob.getLearned().getAbilities()) {
            if (ab.isSpell()) {
                Spell spell = World.getSpell(ab.getId());

                sb.append(String.format(" %1$s %2$-20s %3$-4s %4$-10s %5$s\n", spell
                        .getManaType().getManaString(), ab.getId(), "[" + spell
                        .getCostGivenLevel(playerLevel) + "]", "(" + AbilityConstants
                        .getSkillString(ab.getSkill()) + ")", "{" + spell.getLevel() + "}")
                );

            }

        }

        sb.append("\n").append(Info.BAR2).append("\n");

        mob.out(sb.toString());
    }

}
