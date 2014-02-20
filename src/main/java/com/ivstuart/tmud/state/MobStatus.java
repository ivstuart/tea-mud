package com.ivstuart.tmud.state;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import static com.ivstuart.tmud.state.MobState.*;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MobStatus {

	private Map<MobState, MobStateDuration> stateMap = new HashMap<MobState, MobStateDuration>();

	public void add(MobState state, int durationInSeconds) {
		stateMap.put(state, new MobStateDuration(durationInSeconds));
	}

	public void clear() {
		stateMap.clear();
	}

	public String getPrompt() {
		StringBuilder sb = new StringBuilder("");

		removeExpiredStates();

		for (Map.Entry<MobState, MobStateDuration> stateEntry : stateMap
				.entrySet()) {

			sb.append(stateEntry.getKey().getPrompt());

		}

		return sb.toString();
	}

	public boolean is(MobState state) {

		if (stateMap.containsKey(state) == false) {
			return false;
		}

		if (removeExpiredState(state)) {
			return false;
		}

		return true;
	}

	public boolean isBashAlert() {
		return is(BASH_ALERT);
	}

	public boolean isBashed() {
		return is(BASHED);
	}

	public boolean isBashLagged() {
		return is(BASH_LAGGED);
	}

	public boolean isCasting() {
		return is(CASTING);
	}

	public boolean isGroundFighting() {
		return is(GROUNDFIGHTING);
	}

	public boolean isImmobile() {
		return is(IMMOBILE);
	}

	public boolean isOffBalance() {
		return is(OFFBALANCE);
	}

	boolean removeExpiredState(MobState state) {
		MobStateDuration duration = stateMap.get(state);

		if (duration != null && duration.isExpired()) {
			stateMap.remove(state);
			return true;
		}

		return false;
	}

	void removeExpiredStates() {

		Iterator<Entry<MobState, MobStateDuration>> stateMapIter = stateMap
				.entrySet().iterator();

		for (; stateMapIter.hasNext();) {

			Map.Entry<MobState, MobStateDuration> stateEntry = stateMapIter
					.next();

			if (stateEntry.getValue().isExpired()) {
				stateMapIter.remove();
			}
		}

	}

	public void setBashAlert(int durationInSeconds) {
		add(BASH_ALERT, durationInSeconds);
	}

	public void setBashed(int durationInSeconds) {
		add(BASHED, durationInSeconds);
	}

	public void setBashLagged(int durationInSeconds) {
		add(BASH_LAGGED, durationInSeconds);
	}

	public void setCasting(int durationInSeconds) {
		add(CASTING, durationInSeconds);
	}

	public void setGroundFighting(int durationInSeconds) {
		add(GROUNDFIGHTING, durationInSeconds);
	}

	public void setOffBalance(int durationInSeconds) {
		add(OFFBALANCE, durationInSeconds);
	}

	@Override
	public final String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public boolean isCircling() {
		return is(CIRCLING);
	}

	public void setCircling(int durationInSeconds) {
		add(CIRCLING,durationInSeconds);
	}
}
