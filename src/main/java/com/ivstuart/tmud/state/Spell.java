package com.ivstuart.tmud.state;

import com.ivstuart.tmud.constants.ManaType;
import com.ivstuart.tmud.spells.BuffStats;
import com.ivstuart.tmud.spells.SpellEffect;
import com.ivstuart.tmud.spells.SpellEffectFactory;

public class Spell extends BaseSkill {

	private static final long serialVersionUID = 1L;

	public static Spell NULL = new Spell();

	private ManaType _mana;

	private String _targets;

	// TODO new - so think about this design
	private SpellEffect _spellEffect; // Note a spell behaviour can reference
										// another spell behaviour

	// Default is damage
	private String _stat;

	/**
	 * Cost of spell is double when your level is the same as the spell and this
	 * drop by .2 for next 4 level until it drops to base cost.
	 * 
	 * @param level
	 * @return
	 */
	public int getCostGivenLevel(int level) {
		int levelDiff = this.level + 5 - level;
		if (levelDiff > 0) {
			return (int) (cost * 1.2 * levelDiff);
		}
		return cost;
	}

	public String getDescription() {
		return String.format(" %1$4s %2$10s [%3$s]", this.getManaType()
				.getManaString(), getId(), cost);
	}

	public ManaType getManaType() {
		if (_mana == null) {
			return ManaType.COMMON;
		}
		return _mana;
	}

	public SpellEffect getSpellEffect() {
		if (_spellEffect == null) {
			return SpellEffectFactory.DAMAGE;
		}

		return _spellEffect;
	}

	public String getTarget() {
		return _targets;
	}

	@Override
	public boolean isSkill() {
		return false;
	}

	public void setMana(String mana_) {
		_mana = ManaType.valueOf(mana_);
	}

	public void setSpellEffect(String txt_) {
		_spellEffect = SpellEffectFactory.get(txt_.trim());
	}

	public void setTarget(String targets_) {
		_targets = targets_;

	}

	public void setStat(String stat_) {
		_stat = stat_;

		if (_spellEffect != null && _spellEffect instanceof BuffStats) {
			((BuffStats)_spellEffect).setStat(stat_);
		}

	}

	public String getStat() {
		if (_spellEffect != null && _spellEffect instanceof BuffStats) {
			return ((BuffStats)_spellEffect).getStat();
		}
		return null;
	}

    public boolean isAnyTarget() {
    	return _targets.indexOf("ANY") > -1;
    }
}
