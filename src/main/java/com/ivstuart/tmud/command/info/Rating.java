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

        int rating = getRating(mob);

		mob.out("Your current rating is " + rating);

	}

    public static int getRating(Mob mob) {
        int rating = mob.getPlayer().getData().getLevel();

        rating += mob.getPlayer().getAttributes().getTotal();

        if (mob.getMobAffects().hasAffect("sanctury")) { rating += 30; }
        if (mob.getMobAffects().hasAffect("blur")) { rating += 10; }
        // etc ....
        return rating;
    }

}
