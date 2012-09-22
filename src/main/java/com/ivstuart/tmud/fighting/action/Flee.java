/*
 * Created on 24-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.fighting.action;

import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.person.movement.MoveManager.random;

;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Flee extends FightAction {

	/**
	 * @param character
	 */
	public Flee(Mob character) {
		super(character, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fighting.FightAction#begin()
	 */
	@Override
	public void begin() {
		// TODO Auto-generated method stub
		out("You start looking for an opening to flee");
		duration(1);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fighting.FightAction#changed()
	 */
	@Override
	public void changed() {
		// TODO Auto-generated method stub
		out("You change from fleeing from someone");
		// duration(2000);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fighting.FightAction#destory()
	 */
	@Override
	public void destory() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fighting.FightAction#ended()
	 */
	@Override
	public void ended() {
		// TODO Auto-generated method stub
		out("You end from fleeing");
		duration(5);
	}

	private void fail() {
		out("You fail to flee!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fighting.FightAction#happen()
	 */
	@Override
	public void happen() {
		// TODO Change to get flee chance based on attacker and defender
		if (isSuccess(50)) {
			success();
		} else {
			fail();
		}
		duration(3);

	}

	@Override
	public boolean isMeleeEnabled() {
		return false;
	}

	private boolean isSuccess(int percentage) {
		if (percentage > 95) {
			percentage = 95;
		}
		if (Math.random() * 100 < percentage) {
			return true;
		}
		return false;
	}

	private void success() {
		/* 1st room to flee into */
		Exit exit = random(getSelf());
		if (exit == null) {
			out("You can't get away!");

		} else {
			out("Attempting to flee in " + exit.getId() + " direction.");
			out("You succeed in fleeing a room!");
		}
	}
}
