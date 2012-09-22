package com.ivstuart.tmud.fighting.action;

public enum FightState {

	BEGIN() {

		@Override
		public FightState next(FightAction action) {
			action.begin();
			return HAPPEN;
		}

	},
	HAPPEN() {

		@Override
		public FightState next(FightAction action) {
			action.happen();
			return DONE;
		}

	},
	CHANGED() {

		@Override
		public FightState next(FightAction action) {
			action.changed();
			return DONE;
		}
	},
	DONE() {

		@Override
		public FightState next(FightAction action) {
			action.ended();
			return FINISHED;
		}
	},
	FINISHED() {

		@Override
		public boolean isFinished() {
			return true;
		}

		@Override
		public FightState next(FightAction action) {
			return FINISHED;
		}
	};

	public boolean isFinished() {
		return false;
	}

	public abstract FightState next(FightAction action);

}
