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
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.communication;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class WriteOnPaper extends BaseCommand {

	/**
	 * Have a write on command for writing to signs
	 *  notice boards
	 *  paper etc...
	 */
	@Override
	public void execute(Mob mob, String input) {
		
		// Check pen and paper first
		
		Item note = new Item();
		
		note.setLong(input);
		note.setAlias("note");

		if (input.length() > 40) {
			note.setBrief(input.substring(0, 40));
		} else {
			note.setBrief(input);
		}
		
		// Add finished note to inventory.
		mob.getInventory().add(note);
		
	}

}
