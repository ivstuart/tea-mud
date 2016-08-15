/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.misc;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Alias extends BaseCommand {

	// Each player has their own alias mappings

	@Override
	public void execute(Mob mob, String input) {

		// mm cast magic missile
		if (input.length() == 0) {
			mob.getPlayer().showAlias();
			return;
		}

		String words[] = input.split(" ",2);

		if (words.length == 2) {
			mob.getPlayer().addAlias(words[0],words[1]);
			mob.out("Adding alias '"+words[0]+"' as '"+words[1]+"'");
		}
		else if (words.length == 1) {
			mob.getPlayer().removeAlias(words[0]);
			mob.out("Removing alias '"+words[0]);
		}
		else {
			mob.out("Nothing to alias");
		}

	}

}
