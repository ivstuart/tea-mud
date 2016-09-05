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
	private boolean isAlignmentGuard;

	public GuardMob() {
		super();
	}

    public GuardMob(GuardMob baseMob) {
        super(baseMob);
        this.exit = baseMob.exit;
		isAlignmentGuard = baseMob.isAlignmentGuard;
	}

    public boolean isAlignmentGuard() {
        return isAlignmentGuard;
    }

    public void setAlignmentGuard(boolean alignmentGuard) {
        isAlignmentGuard = alignmentGuard;

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
