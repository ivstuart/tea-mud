package com.ivstuart.tmud.state;

public class Food extends Item {

	private static final long serialVersionUID = -6124978537993047365L;

	private int _portions = 2;
    private boolean isCookable;
    private boolean isSaltable;

	public Food() {

	}

    public boolean isSaltable() {
        return isSaltable;
    }

    public void setSaltable(boolean saltable) {
        isSaltable = saltable;
    }

    public boolean isCookable() {
        return isCookable;
    }

    public void setCookable(boolean cookable) {
        isCookable = cookable;
    }

	public void eat() {
		_portions--;
	}

	public int getPortions() {
		return _portions;
	}

    public void setPortions(int drafts_) {
        _portions = drafts_;
    }

	public void setNumberPortions(int drafts_) {
		_portions = drafts_;
	}
}
