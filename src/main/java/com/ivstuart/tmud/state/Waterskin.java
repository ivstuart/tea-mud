package com.ivstuart.tmud.state;

public class Waterskin extends Item {

	private static final long serialVersionUID = -4557556852795411856L;

	// TODO do we want to have difficult qualities of water...
	private int capacity = 4;
	private int drafts = 4;

	public Waterskin() {

	}

	public void drink() {
		drafts--;
	}

	public void empty() {
		drafts = 0;
	}

	public void fill() {
		drafts = capacity;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getDrafts() {
		return drafts;
	}

	public void setCapacity(int capacity_) {
		capacity = capacity_;
	}

	public void setCapacity(String capacity_) {
		capacity = Integer.parseInt(capacity_.trim());
	}

	public void setDrafts(int drafts_) {
		drafts = drafts_;
	}

	public void setDrafts(String drafts_) {
		drafts = Integer.parseInt(drafts_.trim());
	}
}
