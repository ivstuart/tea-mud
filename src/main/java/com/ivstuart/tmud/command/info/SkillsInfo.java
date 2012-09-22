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

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SkillsInfo implements Command {

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {
		StringBuilder sb = new StringBuilder();

		sb.append(Info.BAR).append("\n");

		for (Ability ab : mob.getLearned().getAbilities()) {
			if (ab.isSkill()) {
				sb.append(String.format(" %1$14s (%2$s)\n", ab.getId(),
						AbilityConstants.getSkillString(ab.getSkill())));
			}

		}

		sb.append(Info.BAR).append("\n");

		mob.out(sb.toString());

	}

}
