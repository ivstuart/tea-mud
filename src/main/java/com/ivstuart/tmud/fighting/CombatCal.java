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

package com.ivstuart.tmud.fighting;

import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.items.Weapon;

import static com.ivstuart.tmud.constants.SkillNames.UNARMED_COMBAT;
import static com.ivstuart.tmud.constants.SpellNames.BLINDNESS;
import static com.ivstuart.tmud.constants.SpellNames.COMBAT_SENSE;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CombatCal {

    public static int getAttack(Mob mob) {
        int attack = getBaseAttack(mob);

        // Get skill level with currently wielded weapon

        Weapon weapon = mob.getEquipment().getWeapon();

        if (weapon != null) {

            int skill = mob.getLearned().getAbility(weapon.getSkill())
                    .getSkill();

            attack += skill;
        } else {
            int skill = mob.getLearned().getAbility(UNARMED_COMBAT)
                    .getSkill();

            attack += skill;
        }

        attack += mob.getEquipment().getHitRollBonus();


        return attack;
    }

    private static int getBaseAttack(Mob mob_) {
        int total = mob_.getOffensive();
        if (mob_.isPlayer()) {
            total += mob_.getPlayer().getAttributes().getSTR().getValue() * 2;
            total += mob_.getPlayer().getAttributes().getDEX().getValue();
            total += mob_.getPlayer().getAttributes().getINT().getValue();
        }

        if (mob_.getMobAffects().hasAffect(BLINDNESS)) {
            total /= 2;
        }

        if (mob_.getMobAffects().hasAffect(COMBAT_SENSE)) {
            total += 20;
        }


        return total;
    }

    private static int getBaseDefence(Mob mob_) {
        if (mob_ == null) {
            return 0;
        }
        int total = mob_.getDefence();
        if (mob_.isPlayer()) {
            total += mob_.getPlayer().getAttributes().getSTR().getValue();
            total += mob_.getPlayer().getAttributes().getDEX().getValue() * 2;
            total += mob_.getPlayer().getAttributes().getINT().getValue();
        }

        if (mob_.getMobAffects().hasAffect(BLINDNESS)) {
            total /= 2;
        }

        if (mob_.getMobAffects().hasAffect(COMBAT_SENSE)) {
            total += 20;
        }

        return total;
    }

    public static int getDefence(Mob mob) {
        int defence = getBaseDefence(mob);

        // Get skill level with currently wielded weapon
        Weapon weapon = mob.getEquipment().getWeapon();

        if (weapon != null) {

            int skill = mob.getLearned().getAbility(weapon.getSkill())
                    .getSkill();

            defence += skill;
        } else {
            int skill = mob.getLearned().getAbility(UNARMED_COMBAT)
                    .getSkill();

            defence += skill;
        }

        return defence;
    }

}
