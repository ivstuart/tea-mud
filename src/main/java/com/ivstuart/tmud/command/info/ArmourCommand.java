/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Armour;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.constants.UsefulContants.armourString;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ArmourCommand implements Command {

	private String desc(int value_) {
		int index = value_ / 5;

		if (index > armourString.length - 1) {
			index = armourString.length - 1;
		}
		return armourString[index];
	}

	@Override
	public void execute(Mob mob, String input) {

		Armour armour = mob.getEquipment().getTotalArmour(); // .armourAt(HEAD);

		// armour.buff(15);

		String data = "$K~$J";
		data += "\n   O      <---   Head/Neck: " + desc(armour.getHead());
		data += "\n /-|-\\    <---  Upper Body: " + desc(armour.getBody());
		data += "\n | | |    <---   Arms/Body: " + desc(armour.getArms());
		data += "\n ! | !    <--- Hands/Waist: " + desc(armour.getWaist());
		data += "\n  / \\     <---        Legs: " + desc(armour.getLegs());
		data += "\n_/   \\_   <--- Ankles/Feet: " + desc(armour.getFeet());
		data += "\n$K~$J                     Overall ---> "
				+ desc(armour.getHead());
		data += "\n$K~$J";

		mob.out(data);
	}

}
