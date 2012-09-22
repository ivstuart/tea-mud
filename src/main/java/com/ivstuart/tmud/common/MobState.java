package com.ivstuart.tmud.common;

public enum MobState {
	SLEEP("sleeping", 3, 3, 2), REST("resting", 2, 2, 2), MEDITATE("mediating",
			1, 1, 3), SIT("sitting", 2, 2, 1), STAND("standing", 1, 1, 1, true), FLYING(
			"being wide awake", 1, 1, 1, true), WAKE("being wide awake", 1, 1,
			1, true); // remove this one??

	public static MobState getMobState(String state_) {
		try {
			return MobState.valueOf(state_);
		} catch (IllegalArgumentException e) {

		}
		return null;
	}

	private final String _desc;
	private short _hpMod;
	private short _mvMod;
	private short _manaMod;

	private boolean canMove;

	MobState(String desc_, int hp_, int mv_, int mana_) {
		_desc = desc_;
		_hpMod = (short) hp_;
		_mvMod = (short) mv_;
		_manaMod = (short) mana_;
		this.canMove = false;
	}

	MobState(String desc_, int hp_, int mv_, int mana_, boolean canMove) {
		this(desc_, hp_, mv_, mana_);
		this.canMove = true;
	}

	public boolean canMove() {
		return canMove;
	}

	public String getDesc() {
		return _desc;
	}

	public short getHpMod() {
		return _hpMod;
	}

	public short getManaMod() {
		return _manaMod;
	}

	public short getMoveMod() {
		return _mvMod;
	}
}
