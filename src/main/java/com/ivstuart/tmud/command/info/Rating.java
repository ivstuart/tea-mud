/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Rating extends BaseCommand {

	/**
	 * 
	 */
	public Rating() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {
		// TODO Auto-generated method stub
		// int rating = mob.getStats().getAttributes().getBaseAttack();
		// mob.out("Your current rating is " + rating);

		mob.out("Not done yet!");
		// World.getTopTen().add(mob.getName(),rating);
	}

}
