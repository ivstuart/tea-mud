package com.ivstuart.tmud.state;

public class Food extends Item {

	private static final long serialVersionUID = -6124978537993047365L;

	private int _portions = 2;

	public Food() {

	}

	public void eat() {
		_portions--;
	}

	public int getPortions() {
		return _portions;
	}

	public void setPortions(String drafts_) {
		_portions = Integer.parseInt(drafts_.trim());
	}

	public void setNumberPortions(int drafts_) {
		_portions = drafts_;
	}
}
