/*
 *  Copyright 2024. Ivan Stuart
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

package com.ivstuart.tmud.state.skills;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.constants.DamageType;
import com.ivstuart.tmud.constants.ManaType;
import com.ivstuart.tmud.spells.BuffStats;
import com.ivstuart.tmud.spells.SpellEffect;
import com.ivstuart.tmud.spells.SpellEffectFactory;

public class Spell extends BaseSkill {

    private static final long serialVersionUID = 1L;

    public static Spell NULL = new Spell();

    private ManaType _mana;

    private String _targets;

    private SpellEffect _spellEffect;

    private String _stat;

    private DiceRoll amount;
    private DamageType damageType;

    public DamageType getDamageType() {

        // Defaults
        if (damageType == null) {
            switch (_mana) {
                case FIRE:
                    return DamageType.FIRE;
                case COMMON:
                    return DamageType.ARCANE;
                case AIR:
                    return DamageType.SHOCK;
                case WATER:
                    return DamageType.COLD;
                case EARTH:
                    return DamageType.NATURE;
                default:
                    return DamageType.PHYSICAL;
            }
        }

        return damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = DamageType.valueOf(damageType);
    }

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

    public void setSpellEffect(String txt_) {
        _spellEffect = SpellEffectFactory.get(txt_.trim());
    }

    public String getTarget() {
        return _targets;
    }

    public void setTarget(String targets_) {
        _targets = targets_;

    }

    @Override
    public boolean isSkill() {
        return false;
    }

    public void setMana(ManaType mana_) {
        _mana = mana_;
    }

    public String getStat() {
        if (_spellEffect != null && _spellEffect instanceof BuffStats) {
            return ((BuffStats) _spellEffect).getStat();
        }
        return null;
    }

    public void setStat(String stat_) {
        _stat = stat_;

        if (_spellEffect != null && _spellEffect instanceof BuffStats) {
            ((BuffStats) _spellEffect).setStat(stat_);
        }

    }

    public boolean isAnyTarget() {
        if (_targets == null) {
            return false;
        }
        return _targets.contains("ANY");
    }

    public int getAmount() {
        if (amount == null) {
            return 0;
        }
        return amount.roll();
    }

    public void setAmount(String amount) {
        this.amount = new DiceRoll(amount);
    }
}
