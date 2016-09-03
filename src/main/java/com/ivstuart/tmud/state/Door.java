package com.ivstuart.tmud.state;

import com.ivstuart.tmud.constants.DoorState;

import static com.ivstuart.tmud.constants.DoorState.CLOSED;

public class Door extends BasicThing {

	private static final long serialVersionUID = 1L;

	protected DoorState state = CLOSED;

	protected String keyId;

	protected int hp;
	protected boolean isBashable;
	protected boolean isPickable;
	protected boolean isUnspellable;

	public Door() {
	}

	public boolean isBashable() {
		return isBashable;
	}

	public void setBashable(boolean bashable) {
		isBashable = bashable;
	}

	public boolean isPickable() {
		return isPickable;
	}

	public void setPickable(boolean pickable) {
		isPickable = pickable;
	}

	public boolean isUnspellable() {
		return isUnspellable;
	}

	public void setUnspellable(boolean unspellable) {
		isUnspellable = unspellable;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String id) {
		keyId = id;
	}

	public DoorState getState() {
		return state;
	}

	public void setState(DoorState state_) {
		state = state_;
	}

	public boolean isLockable() {
		return true;
	}

}
