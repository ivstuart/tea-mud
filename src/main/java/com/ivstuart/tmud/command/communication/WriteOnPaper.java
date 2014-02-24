/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.communication;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class WriteOnPaper implements Command {

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
		note.setBrief(input.substring(0,40));
		
		// Add finished note to inventory.
		mob.getInventory().add(note);
		
	}

}
