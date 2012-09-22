/*
 * Created on 17-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command;

import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface Command {

	/**
	 * 
	 * @param mob
	 * @param input
	 *            calling code ensures that null is never passed in
	 */
	public abstract void execute(Mob mob, String input);

	// public abstract String getHelp();
}
