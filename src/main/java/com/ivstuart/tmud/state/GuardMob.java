package com.ivstuart.tmud.state;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuardMob extends Mob {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3758337691304906202L;

	private static final Logger LOGGER = LogManager.getLogger();

	private String exit;

	public boolean isAlignmentGuard() {
		return isAlignmentGuard;
	}

	public void setAlignmentGuard(String alignmentGuard) {
		isAlignmentGuard = Boolean.valueOf(alignmentGuard);
		;
	}

	private boolean isAlignmentGuard;

	public GuardMob() {
		super();
	}

	public GuardMob(Mob baseMob) {
		super(baseMob);
		this.exit = ((GuardMob) baseMob).exit;
		isAlignmentGuard = false;
	}

	@Override
	public boolean isGuard() {
		return true;
	}

	@Override
	public boolean isGuarding(Mob mob_, String id) {

		LOGGER.debug("id [ " + id + " ] exit [ " + exit + " ]");

		if (isDead()) {
			return false;
		}

		if (isAlignmentGuard && mob_.isGood() == this.isGood()) {
			LOGGER.debug("Alignment guard and they match so may pass");
			return false;
		}

		return id.equals(exit);

	}

	public void setGuardExit(String direction) {
		this.exit = direction;
	}
}
