package com.ivstuart.tmud.state;

import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.common.Msgable;
import com.ivstuart.tmud.common.Tickable;

@SuppressWarnings("serial")
public class Torch extends Item implements Tickable {

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

	public boolean isLit() {
		return _isLit;
	}

	@Override
	public boolean isTorch() {
		return true;
	}

	public void setLit(boolean isLit_) {
		_isLit = isLit_;

	}

	public void setMsgable(Msgable mob_) {
		_msg = mob_;
	}

	@Override
	public void tick() {
		if (_isLit) {
			// TODO A torch is near which flickers as it burns.
			// TODO Your torch flickers as it burns
			_msg.out(new Msg("<S-Your/NAME> light source flickers!"));
			_fuel--;
			if (_fuel <= 0) {
				_msg.out(new Msg("Your light source flickers and dies!"));
				_isLit = false;
			}
		}
	}
}
