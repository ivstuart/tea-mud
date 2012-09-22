/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.World;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Users implements Command {

	@Override
	public void execute(Mob mob, String input) {
		// TODO list
		// "Num Class   Name         State          Idl Login@   Site\r\n"
		// "--- ------- ------------ -------------- --- -------- ------------------------\r\n");

		mob.out("$~");

		for (String playerName : World.getPlayers()) {
			Player player = World.getMob(playerName).getPlayer();
			String line = playerName + player.getData().getTitle();
			mob.out(line);
		}

		mob.out("$~");
	}

}
