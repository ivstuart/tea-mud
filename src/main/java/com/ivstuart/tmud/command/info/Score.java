/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.common.Info;
import com.ivstuart.tmud.fighting.CombatCal;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.person.PlayerData;
import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.common.Colour.*;
import static com.ivstuart.tmud.constants.ManaType.*;

/**
 * @author stuarti
 * 
 * 
 * 
 * 
 * 
 *         You have 0 copper banked, and carry 0 platinum, 0 gold and 0 copper.
 *         You carry 17 stones and 3 pebbles of weight. You are unburdened.
 * 
 */
public class Score extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		String mAtt = "" + CombatCal.getAttack(mob);
		String mDef = "" + CombatCal.getDefence(mob);

		String mBatt = "1";

		Player player = mob.getPlayer();

		PlayerData data = player.getData();

		mob.out(Info.BAR);
		mob.out("                      --- Pattern Character Sheet ---");
		mob.out(Info.BAR);
		mob.out("   You are " + mob.getId() + ", level " + data.getLevel()
				+ " amongst your " + mob.getRace() + " ancestors.\n");

		mob.out("   You are " + player.getData().getAge() + " years of age ("
				+ player.getData().getPlayingFor() + " Days, "
				+ player.getData().getPlayingFor() + " Hours) old. You are "
				+ mob.getHp().getValue() + " tall.");
		mob.out("   You have gained " + player.getData().getTotalXp()
				+ " experience points " + player.getData().getRemorts()
				+ " remort.");
		mob.out("   You may learn " + data.getLearns()
				+ " new skills or spells with your experience pool of "
				+ data.getPracs() + "\n");

		mob.out(String
				.format("  %1$2s Health     : %2$6s    Damage Bonus : %3$6s    War Points  : %4$6s",
						WHITE.toString(), mob.getHp().getValue(), "3", "0"));
		mob.out(String
				.format("  %1$2s Stamina    : %2$6s    Melee Attack : %3$6s    Kill Points : %4$6s",
						WHITE.toString(), mob.getMv().getValue(), mAtt, "0"));

		MobMana mana = mob.getMana();

		if (mana == null) {
			mob.out("   You have no magical power");

		} else {

			mob.out(String
					.format("  %1$2s Mana       : %2$6s    Melee Defence: %3$6s    Ability Pts : %4$6s",
							WHITE.toString(), mana.getPrompt(), mDef, "0"));

			mob.out(String
					.format("  %1$2s Fire  Save : %2$6s    Ranged Attack: %3$6s    Rating      : %4$6s",
							RED.toString(), mana.get(FIRE).getPrompt(), mBatt,
							"rating"));
			mob.out(String
					.format("  %1$2s Earth Save : %2$6s    Evasion      : %3$6s    Remorts     : %4$6s",
							BROWN.toString(), mana.get(EARTH).getPrompt(),
							"EVASION", "REMORTS"));
			mob.out(String
					.format("  %1$2s Water Save : %2$6s    Stealth      : %3$6s    Level       : %4$6s",
							BLUE.toString(), mana.get(WATER).getPrompt(),
							"STEALTH", data.getLevel()));
			mob.out(String
					.format("  %1$2s Air   Sav  : %2$6s    Perception   : %3$6s    Deaths      : %4$6s",
							YELLOW.toString(), mana.get(AIR).getPrompt(),
							"PERCEPTION", "DEATHS"));

		}

		mob.out("" + WHITE);

		mob.out("   You have 0 copper banked, and carry 0 platinum, 0 gold and 0 copper.");
		mob.out("   You carry 20.411 kgs of weight. You are unburdened.");
		mob.out("");

		mob.out(Info.BAR);

	}

}
