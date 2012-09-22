/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.common.Info;
import com.ivstuart.tmud.constants.AbilityConstants;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;
import com.ivstuart.tmud.state.World;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SpellsInfo implements Command {

	@Override
	public void execute(Mob mob, String input) {

		StringBuilder sb = new StringBuilder();

		int playerLevel = mob.getPlayer().getData().getLevel();

		sb.append(Info.BAR2).append("\n");

		for (Ability ab : mob.getLearned().getAbilities()) {
			if (ab.isSpell()) {
				Spell spell = World.getSpell(ab.getId());

				sb.append(String.format(" %1$s %2$s [%3$s] (%4$s)\n", spell
						.getManaType().getManaString(), ab.getId(), spell
						.getCostGivenLevel(playerLevel), AbilityConstants
						.getSkillString(ab.getSkill())));

			}

		}

		sb.append("\n").append(Info.BAR2).append("\n");

		mob.out(sb.toString());
	}

}
