/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.ivstuart.tmud.state;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import static com.ivstuart.tmud.state.MobCombatState.*;

public class MobStatus {

    private Map<MobCombatState, MobStateDuration> stateMap = new HashMap<MobCombatState, MobStateDuration>();

	@Override
	public String toString() {
		return "MobStatus{" +
				"stateMap=" + stateMap +
				'}';
	}

	public void add(MobCombatState state, int durationInSeconds) {
		stateMap.put(state, new MobStateDuration(durationInSeconds));
	}

	public void clear() {
		stateMap.clear();
	}

	public String getPrompt() {
		StringBuilder sb = new StringBuilder("");

		removeExpiredStates();

		for (Map.Entry<MobCombatState, MobStateDuration> stateEntry : stateMap
				.entrySet()) {

			sb.append(stateEntry.getKey().getPrompt());

		}

		return sb.toString();
	}

	public boolean is(MobCombatState state) {

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

    public void setBashAlert(int durationInSeconds) {
        add(BASH_ALERT, durationInSeconds);
    }

	public boolean isBashed() {
		return is(BASHED);
	}

    public void setBashed(int durationInSeconds) {
        add(BASHED, durationInSeconds);
    }

	public boolean isBashLagged() {
		return is(BASH_LAGGED);
	}

    public void setBashLagged(int durationInSeconds) {
        add(BASH_LAGGED, durationInSeconds);
    }

	public boolean isCasting() {
		return is(CASTING);
	}

    public void setCasting(int durationInSeconds) {
        add(CASTING, durationInSeconds);
    }

	public boolean isGroundFighting() {
		return is(GROUNDFIGHTING);
	}

    public void setGroundFighting(int durationInSeconds) {
        add(GROUNDFIGHTING, durationInSeconds);
    }

	public boolean isImmobile() {
		return is(IMMOBILE);
	}

	public boolean isOffBalance() {
		return is(OFFBALANCE);
	}

    public void setOffBalance(int durationInSeconds) {
        add(OFFBALANCE, durationInSeconds);
    }

	boolean removeExpiredState(MobCombatState state) {
		MobStateDuration duration = stateMap.get(state);

		if (duration != null && duration.isExpired()) {
			stateMap.remove(state);
			return true;
		}

		return false;
	}

	void removeExpiredStates() {

		Iterator<Entry<MobCombatState, MobStateDuration>> stateMapIter = stateMap
				.entrySet().iterator();

		for (; stateMapIter.hasNext();) {

			Map.Entry<MobCombatState, MobStateDuration> stateEntry = stateMapIter
					.next();

			if (stateEntry.getValue().isExpired()) {
				stateMapIter.remove();
			}
		}

	}

	public boolean isCircling() {
		return is(CIRCLING);
	}

	public void setCircling(int durationInSeconds) {
		add(CIRCLING,durationInSeconds);
	}

	public boolean isHidden() {
		return is(HIDDEN);
	}

    public void setHidden(int durationInSeconds) {
        add(HIDDEN, durationInSeconds);
    }

	public boolean isSneaking() {
		return is(SNEAKING);
	}
	
	public void setSneaking(int durationInSeconds) {
		add(SNEAKING,durationInSeconds);
	}

	public boolean isFrozen() {
		return is(FROZEN);
    }

    public void setFrozen(int durationInSeconds) {
        add(FROZEN, durationInSeconds);
    }
}
