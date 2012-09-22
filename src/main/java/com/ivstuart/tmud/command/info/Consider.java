/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Consider implements Command {

	@Override
	public void execute(Mob mob, String input) {
		// TODO Auto-generated method stub
		mob.out("Consider not implemented yet");

		// Level difference is no good enough todo a comparison.
		// Use Hp + Arm + dam at least to provide a rating.

		// Consider killing who?

		// You can not kill yourself (maybe allow this)

		// Would you like to borrow a cross and a shovel?

		/**
		 * if (diff <= -10) send_to_char(ch,
		 * "Now where did that chicken go?\r\n"); else if (diff <= -5)
		 * send_to_char(ch, "You could do it with a needle!\r\n"); else if (diff
		 * <= -2) send_to_char(ch, "Easy.\r\n"); else if (diff <= -1)
		 * send_to_char(ch, "Fairly easy.\r\n"); else if (diff == 0)
		 * send_to_char(ch, "The perfect match!\r\n"); else if (diff <= 1)
		 * send_to_char(ch, "You would need some luck!\r\n"); else if (diff <=
		 * 2) send_to_char(ch, "You would need a lot of luck!\r\n"); else if
		 * (diff <= 3) send_to_char(ch,
		 * "You would need a lot of luck and great equipment!\r\n"); else if
		 * (diff <= 5) send_to_char(ch, "Do you feel lucky, punk?\r\n"); else if
		 * (diff <= 10) send_to_char(ch, "Are you mad!?\r\n"); else if (diff <=
		 * 100) send_to_char(ch, "You ARE mad!\r\n");
		 */

		/**
		 * Mob target = getTarget();
		 * 
		 * if (target == null) { out(input + " is not here to consider!");
		 * return; } // Get own stats (i.e. hp) and conpare them with monster //
		 * target.getStats();
		 */
	}

}
