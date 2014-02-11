/*
 * Created on 12-Oct-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.constants.FightConstants;
import com.ivstuart.tmud.person.statistics.ManaAttribute;
import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.state.Attribute;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.common.Colour.*;
import static com.ivstuart.tmud.constants.ManaType.*;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Prompt implements Command {

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

	public static void show(Mob mob) {

		if (!mob.isPlayer()) {
			return;
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
			return;
		}

		Attribute hp = mob.getHp();
		Attribute mv = mob.getMv();

		ManaAttribute fm = mana.get(FIRE);
		ManaAttribute em = mana.get(EARTH);
		ManaAttribute wm = mana.get(WATER);
		ManaAttribute am = mana.get(AIR);

		sb.append(GREEN + hp.getPrompt() + "Hp ");
		sb.append(BLUE + mv.getPrompt() + "Mv ");

		sb.append(" $H<$I");
	
		sb.append(RED + fm.getPrompt() + "Fi ");
		sb.append(BROWN + em.getPrompt() + "Ea ");
		sb.append(BLUE + wm.getPrompt() + "Wa ");
		sb.append(YELLOW + am.getPrompt() + "Ai ");

		sb.append("$H>$J");

		// Kgs:
		sb.append("Kgs:0.0 ");

		// ( xp )
		sb.append("(");

		sb.append(mob.getPlayer().getData().getToLevelXp());

		// TODO
		// sb.append(ent.getStats().getMiscStats().getToLevelXp());
		sb.append(")");

		// [Oppnt: good]
		if (mob.getFight() != null) {
			Mob target = mob.getFight().getTarget();
			if (target != null) {
				// sb.append(" [Oppnt: ");
				sb.append(" [Op: ");
				sb.append(getLifeStatus(target));
				// TODO refactor
				sb.append(target.getMobStatus().getPrompt());
				sb.append("]");
			}
		}
		mob.out(sb.toString());
	}

	@Override
	public void execute(Mob mob_, String input_) {
		Prompt.show(mob_);
	}
}
