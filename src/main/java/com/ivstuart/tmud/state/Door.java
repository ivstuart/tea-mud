package com.ivstuart.tmud.state;

import static com.ivstuart.tmud.constants.DoorState.CLOSED;

import com.ivstuart.tmud.constants.DoorState;

public class Door extends BasicThing {

	private static final long serialVersionUID = 1L;

	protected DoorState state = CLOSED;

	protected String keyId;

	protected int hp;

	public Door() {
	}

	public String getKeyId() {
		return keyId;
	}

	public DoorState getState() {
		return state;
	}

	public boolean isLockable() {
		return true;
	}

	public void setKeyId(String id) {
		keyId = id;
	}

	public void setState(DoorState state_) {
		state = state_;
	}

}
