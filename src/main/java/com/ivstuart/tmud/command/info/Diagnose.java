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
public class Diagnose implements Command {

	@Override
	public void execute(Mob mob, String input) {
		// TODO Auto-generated method stub
		mob.out("Consider not implemented yet");

		/**
		 * { 100, "is in excellent condition." }, { 90, "has a few scratches."
		 * }, { 75, "has some small wounds and bruises." }, { 50,
		 * "has quite a few wounds." }, { 30,
		 * "has some big nasty wounds and scratches." }, { 15,
		 * "looks pretty hurt." }, { 0, "is in awful condition." }, { -1,
		 * "is bleeding awfully from big wounds." },
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
