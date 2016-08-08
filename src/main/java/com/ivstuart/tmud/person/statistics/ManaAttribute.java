package com.ivstuart.tmud.person.statistics;

import java.io.Serializable;

import com.ivstuart.tmud.constants.ManaType;

public class ManaAttribute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String _name;

	private int _minimum;

	private int _maximum;

	private int _current;

	private int _castlevel;

	private ManaType _mana;

	public ManaAttribute(ManaAttribute old_) {
		_mana = old_._mana;
		_name = old_._name;
		_maximum = old_._maximum;
		_current = old_._current;
		_minimum = old_._minimum;
		_castlevel = old_._castlevel;
	}

	public ManaAttribute(ManaType mana_) {
		_mana = mana_;
		_name = mana_.name();
		_maximum = 10;
		_current = 10;
		_minimum = 0;
		_castlevel = 18; // TODO take int + wis / 2
	}

	public void addCastLevel(int level_) {
		_castlevel += level_;
	}

	public void addMaximum(int max_) {
		_maximum += max_;
	}

	public void decrease(int value) {
		_current -= value;
		if (_current < _minimum) {
			_current = _minimum;
		}
	}

	public String display() {
		return String.format("%1$s << %2$5s >>  $J %3$3s / %4$3s   %5$3s",
				_mana.getColour().toString(), _name, _current, _maximum,
				_castlevel);
	}

	public int getCastlevel() {
		return _castlevel;
	}

	private String getColour(int current_, int max_) {
		if (_current < _maximum) {
			return "$G";
		} else if (_current > _maximum) {
			return "$K";
		}
		return "$J";
	}

	public String getDescription() {
		return String.format("%1%2$3s / $J%3$3s",
				this.getColour(_current, _maximum), _current, _maximum);
	}

	public ManaType getManaType() {
		return _mana;
	}

	public int getMaximum() {
		return _maximum;
	}

	public String getName() {
		return _name;
	}

	public String getPrompt() {
		return String.valueOf(_current);
	}

	public int getValue() {
		return _current;
	}

	public void increase(int value) {
		_current += value;
		if (_current > _maximum) {
			_current = _maximum;
		}
	}

	public void removeCastLevel(int level_) {
		_castlevel -= level_;
	}

	public void removeMaximum(int max_) {
		_maximum -= max_;
		this.decrease(max_);
	}

	public void restore() {
		_current = _maximum;
	}

	public void setCastlevel(int castlevel) {
		this._castlevel = castlevel;
	}

	public void setValue(int value) {
		_current = value;
		this.decrease(0);
		this.increase(0);
	}

}
