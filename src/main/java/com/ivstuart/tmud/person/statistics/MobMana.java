package com.ivstuart.tmud.person.statistics;

import java.io.Serializable;

import com.ivstuart.tmud.constants.ManaType;
import com.ivstuart.tmud.state.Spell;

public class MobMana implements Serializable {

	private static final long serialVersionUID = 1L;

	private ManaAttribute _mana[] = null;

	public MobMana() {
		// initialize(false);
	}

	public MobMana(boolean isPlayer_) {
		initialize(isPlayer_);
	}

	public MobMana(MobMana oldMana_) {
		_mana = new ManaAttribute[oldMana_._mana.length];
		for (int index = 0; index < _mana.length; index++) {
			_mana[index] = new ManaAttribute(oldMana_._mana[index]);
		}
	}

	/**
	 * TODO does a restore too (bad design)
	 * 
	 * @param magic
	 */
	public void addMaximum(int magic) {
		for (ManaAttribute mana : _mana) {
			mana.addMaximum(magic);
			mana.restore();
		}

	}

	public void cast(Spell spell_) {
		ManaType manaType = spell_.getManaType();

		int cost = spell_.getCost();

		if (manaType == ManaType.COMMON) {
			for (int index = 0; index < 4; index++) {
				_mana[index].decrease(cost);
			}
		} else {
			_mana[manaType.ordinal()].decrease(cost);
		}
	}

	public ManaAttribute get(ManaType manaType_) {
		if (_mana == null) {
			return null;
		}
		return _mana[manaType_.ordinal()];
	}

	public String getPrompt() {
		// TODO Auto-generated method stub
		return _mana[0].getPrompt();
	}

	public boolean hasEnoughManaToCast(Spell spell_) {
		if (_mana == null) {
			return false;
		}

		ManaType manaType = spell_.getManaType();

		if (manaType == ManaType.COMMON) {
			for (ManaAttribute mana : _mana) {
				if (mana.getValue() < spell_.getCost()) {
					return false;
				}
			}
		} else {
			if (_mana[manaType.ordinal()].getValue() < spell_.getCost()) {
				return false;
			}
		}
		return true;
	}

	public boolean hasLevelToCast(Spell spell_) {
		if (_mana == null) {
			return false;
		}

		ManaType manaType = spell_.getManaType();

		if (manaType == ManaType.COMMON) {

			for (ManaAttribute mana : _mana) {
				if (mana.getCastlevel() > spell_.getLevel()) {
					return true;
				}
			}
		} else {
			if (_mana[manaType.ordinal()].getCastlevel() > spell_.getLevel()) {
				return true;
			}
		}
		return false;
	}

	public void increase(int value_) {
		for (ManaAttribute mana : _mana) {
			mana.increase(value_);
		}
	}

	private void init(ManaType manaType_) {
		_mana[manaType_.ordinal()] = new ManaAttribute(manaType_);
	}

	private void initialize(boolean isPlayer_) {
		if (isPlayer_) {
			_mana = new ManaAttribute[4];
			init(ManaType.FIRE);
			init(ManaType.EARTH);
			init(ManaType.WATER);
			init(ManaType.AIR);
		} else {
			_mana = new ManaAttribute[1];
			init(ManaType.COMMON);
		}
	}
}
