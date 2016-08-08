/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.person.statistics.Affect;
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

	private String desc(int value_,int buff) {
		int index = value_ + buff / 5;

		if (index > armourString.length - 1) {
			index = armourString.length - 1;
		}
		return armourString[index];
	}

	@Override
	public void execute(Mob mob, String input) {

		Armour armour = mob.getEquipment().getTotalArmour(); // .armourAt(HEAD);

		Affect armourBuff = mob.getMobAffects().getArmourBuff();

		int buff = 0;
		if (armourBuff != null) {
			buff = armourBuff.getBuff();
		}

		String data = "$K~$J";
		data += "\n   O      <---   Head/Neck: " + desc(armour.getHead(),buff);
		data += "\n /-|-\\    <---  Upper Body: " + desc(armour.getBody(),buff);
		data += "\n | | |    <---   Arms/Body: " + desc(armour.getArms(),buff);
		data += "\n ! | !    <--- Hands/Waist: " + desc(armour.getWaist(),buff);
		data += "\n  / \\     <---        Legs: " + desc(armour.getLegs(),buff);
		data += "\n_/   \\_   <--- Ankles/Feet: " + desc(armour.getFeet(),buff);
		data += "\n$K~$J";
		data += "\n               Overall ---> " + desc(armour.getAverage(),buff);
		data += "\n$K~$J";

		mob.out(data);
	}

}
