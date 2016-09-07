/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.constants.DoorState;

import static com.ivstuart.tmud.constants.DoorState.CLOSED;

public class Door extends BasicThing {

	private static final long serialVersionUID = 1L;

	protected DoorState state = CLOSED;

	protected String keyId;

	protected int hp; // Not used yet
	protected boolean isBashable = true;
	protected boolean isPickable = true;
	protected boolean isUnspellable;
	protected int strength;
	protected int difficulty = 50;

	public Door() {
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	@Override
	public String toString() {
		return "Door{" +
				"state=" + state +
				", keyId='" + keyId + '\'' +
				", hp=" + hp +
				", isBashable=" + isBashable +
				", isPickable=" + isPickable +
				", isUnspellable=" + isUnspellable +
				", strength=" + strength +
				'}';
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

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}
}
