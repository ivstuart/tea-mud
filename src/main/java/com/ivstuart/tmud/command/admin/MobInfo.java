/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.World;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MobInfo extends AdminCommand {

	@Override
	public void execute(Mob mob, String input) {
		
		super.execute(mob,input);
		
		Mob target = World.getMob(input);

		if (target == null) {
			mob.out(input + " is not in the world to get all stats on!");
			return;
		}

		mob.out("Damage = "+target.getDamage());

		mob.out(target.toString());

	}

}