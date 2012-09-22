package com.ivstuart.tmud.state;

import org.apache.log4j.Logger;

public class GuardMob extends Mob {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3758337691304906202L;

	private static final Logger LOGGER = Logger.getLogger(GuardMob.class);

	private String exit;

	public GuardMob() {
		super();
	}

	public GuardMob(Mob baseMob) {
		super(baseMob);
		this.exit = ((GuardMob) baseMob).exit;
	}

	@Override
	public boolean isGuard() {
		return true;
	}

	@Override
	public boolean isGuarding(String id) {

		LOGGER.debug("id [ " + id + " ]");
		LOGGER.debug("exit [ " + exit + " ]");

		// TODO FIXME decide if mob would still be here given mobs with zero
		// health are replaced with a corpse.
		if (isDead()) {
			return false;
		}

		return id.equals(exit);

	}

	public void setGuardExit(String direction) {
		this.exit = direction;
	}
}
