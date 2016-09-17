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
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Armour;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.constants.UsefulContants.armourString;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ArmourCommand extends BaseCommand {

	private String desc(int value_) {
		int index = value_;

		if (index > armourString.length - 1) {
			index = armourString.length - 1;
		}
		return armourString[index];
	}

	@Override
	public void execute(Mob mob, String input) {

		Armour armour = mob.getEquipment().getTotalArmour(); // .armourAt(HEAD);

		String data = "$K~$J";
		data += "\n   O      <---   Head/Neck: " + desc(armour.getHead());
		data += "\n /-|-\\    <---  Upper Body: " + desc(armour.getBody());
		data += "\n | | |    <---   Arms/Body: " + desc(armour.getArms());
		data += "\n ! | !    <--- Hands/Waist: " + desc(armour.getWaist());
		data += "\n  / \\     <---        Legs: " + desc(armour.getLegs());
		data += "\n_/   \\_   <--- Ankles/Feet: " + desc(armour.getFeet());
		data += "\n$K~$J";
		data += "\n               Overall ---> " + desc(armour.getAverage());
		data += "\n$K~$J";

		mob.out(data);
	}

}
