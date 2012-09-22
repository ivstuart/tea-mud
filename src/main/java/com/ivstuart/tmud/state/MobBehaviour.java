package com.ivstuart.tmud.state;

public class MobBehaviour {

	// TODO design this
	// A light of behaviours might be better than a
	// list of static preprogrammed flag states

	// Tickable ?

	private boolean isWander = false;
	private boolean isAggressive = false;
	private boolean isGuard = false;

	// TODO refactor into enum

	public boolean isWander() {
		return isWander;
	}

	public void setBehaviour(String behaviour) {
		if (behaviour.indexOf("WANDER") > -1) {
			isWander = true;
		}
	}
}
