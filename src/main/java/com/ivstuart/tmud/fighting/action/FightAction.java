package com.ivstuart.tmud.fighting.action;

import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.MobStatus;

public abstract class FightAction {

	private long whenNextStateMillis;

	private FightState state;

	private Mob self;

	private Mob target;

	public FightAction(Mob me, Mob target) {
		this.self = me;
		this.target = target;
		this.state = FightState.BEGIN;
	}

	public void begin() {
		getSelf().getFight().changeTarget(getTarget());
	}

	public abstract void changed();

	public void destory() {
	}

	protected void duration(long durationSeconds) {
		whenNextStateMillis = System.currentTimeMillis()
				+ (durationSeconds * 1000);
	}

	protected void durationMillis(long durationMillis) {
		whenNextStateMillis = System.currentTimeMillis() + durationMillis;
	}

	public abstract void ended();

	public void finished() {
		setState(FightState.FINISHED);
	}

	protected Fight getFight() {
		return self.getFight();
	}

	protected MobMana getMobMana() {
		return self.getMana();
	}

	public Mob getSelf() {
		return self;
	}

	public Mob getTarget() {
		return target;
	}

	public abstract void happen();

	public boolean isFinished() {
		return state.isFinished();
	}

	public boolean isGroundFighting() {
		return false;
	}

	public boolean isMeleeEnabled() {
		return true;
	}

	public void next() {
		// Log.info("State = "+state);

		if (System.currentTimeMillis() < whenNextStateMillis) {
			return;
		}

		state = state.next(this);
	}

	protected void out(Msg output_) {
		// Need to think about hits to flee characters...
		self.getRoom().out(output_);
		// _self.out(output);
	}

	protected void out(String output) {
		self.out(new Msg(self, target, output));
		// self.out(output);
	}

	public void restart() {
		setState(FightState.BEGIN);
	}

	public void setState(FightState state) {
		this.state = state;
	}

	public void setTarget(Mob mob) {
		this.target = mob;
	}

	@Override
	public final String toString() {
		return state.toString() + " " + self.getName() + " -> "
				+ target.getName();

		// return ToStringBuilder.reflectionToString(this);
	}
	
}