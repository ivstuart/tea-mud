/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import static com.ivstuart.tmud.constants.ManaType.*;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Magic implements Command {

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

		StringBuilder sb = new StringBuilder();
		sb.append("$K~$J");
		sb.append("\n$H   /---\\                    Cur   Max   Level");
		sb.append("\n$H  /  |  \\   " + mana.get(FIRE).display());
		sb.append("\n$H /__/ \\__\\  " + mana.get(EARTH).display());
		sb.append("\n$H \\  \\ /  /  " + mana.get(WATER).display());
		sb.append("\n$H  \\  |  /   " + mana.get(AIR).display());
		sb.append("\n$H   \\---/");
		sb.append("\n$K~$J");

		mob_.out(sb.toString());
	}

}
