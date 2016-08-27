/*
 * Created on 24-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.fighting.action;

import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttackFlurry extends BasicAttack {

	/**
	 * @param character
	 */
	public AttackFlurry(Mob me, Mob target) {
		super(me, target);

		out(new Msg(me, target,
				"<S-You ready your/NAME readies GEN-him>self to attack <T-you/NAME>."));

	}


	@Override
	public void happen() {

		if (!getSelf().getMv().deduct(25)) {
			out("You do not have enough movement available to flurry");
			this.finished();
		}

        getSelf().out("You open out with a flurry of attacks doubling you chances to hit");

		super.happen();
		super.happen();

	}


}
