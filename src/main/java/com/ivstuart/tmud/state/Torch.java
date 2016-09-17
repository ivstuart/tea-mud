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

import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.common.Msgable;
import com.ivstuart.tmud.common.Tickable;

public class Torch extends Item implements Tickable {

	private static final long serialVersionUID = 1L;

	private boolean _isLit = false;

	private int _fuel = 100;

	private Msgable _msg = null;

	public Torch() {

	}

	public int getFuel() {
		return _fuel;
	}

	@Override
	public String getLook() {
		String look = super.getLook();

		if (isLit()) {
			if (_fuel > 20) {
				look += " burns brightly";
			} else {
				look += " burns softly";
			}
		} else {
			if (_fuel <= 0) {
				look += " is burnt out";
			}
		}
		return look;
	}

	@Override
	public String toString() {
		return "Torch{" +
				"_isLit=" + _isLit +
				", _fuel=" + _fuel +
				", _msg=" + _msg +
				'}';
	}

	public boolean isLit() {
		return _isLit;
	}

	public void setLit(boolean isLit_) {
		_isLit = isLit_;

	}

	@Override
	public boolean isTorch() {
		return true;
	}

	public void setMsgable(Msgable msgable) {
		_msg = msgable;
	}

	@Override
    public boolean tick() {

        if (((Mob) _msg).getPlayer().getConnection().isDisconnected()) {
            return true;
        }

		if (_isLit) {
			_msg.out(new Msg("<S-Your/NAME> light source flickers!"));
			_fuel--;
			if (_fuel <= 0) {
				_msg.out(new Msg("Your light source flickers and dies!"));
				_isLit = false;
			}
		}
        return false;
    }
}
