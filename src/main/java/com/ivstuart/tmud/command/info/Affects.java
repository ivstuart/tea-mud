/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Attribute;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Affects extends BaseCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {


        Mob self = mob;

		// get any mob affects
		self.out(mob.showMobAffects());

		self.out(getHunger(self));
		self.out(getThirst(self));

	}

	private String getHunger(Mob self) {
		Attribute att = self.getPlayer().getData().getHunger();
		if (att.getValue() > 400) {
			return "You are fully fed.";
		}
		if (att.getValue() > 150) {
			return "You could eat if forced.";
		}
		if (att.getValue() > 100) {
			return "You are slightly hungery.";
		}
		if (att.getValue() > 50) {
			return "You are hungery.";
		}
		if (att.getValue() > 0) {
			return "You are very hungery.";
		}
		if (att.getValue() > -50) {
			return "You are suffering with starvation!";
		}
		return "You are starving to death!";
	}

	private String getThirst(Mob self) {
		Attribute att = self.getPlayer().getData().getThirst();
		if (att.getValue() > 80) {
			return "You are fully refreshed.";
		}
		if (att.getValue() > 30) {
			return "You could drink if forced to.";
		}
		if (att.getValue() > 20) {
			return "You are slightly thirsty.";
		}
		if (att.getValue() > 10) {
			return "You are thirsty.";
		}
		if (att.getValue() > 0) {
			return "You are very thirsty.";
		}
		if (att.getValue() > -10) {
			return "You are suffering with dehyration!";
		}
		return "Dehyration is killing you!";
	}

}
