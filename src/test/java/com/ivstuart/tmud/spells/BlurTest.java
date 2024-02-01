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

package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.constants.ManaType;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.skills.Spell;
import com.ivstuart.tmud.utils.TestHelper;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Ivan on 28/09/2016.
 */
public class BlurTest {

    @Test
    public void testBlur() {

        Mob mob = TestHelper.makeDefaultPlayerMob("Ivan");
        Mob ste = TestHelper.makeDefaultPlayerMob("Ste");

        Spell spell = new Spell();
        spell.setMana(ManaType.WATER);
        spell.setId("blur");
        spell.setDuration("100");

        Blur blur = new Blur();
        blur.effect(mob, mob, spell, null);

        assertEquals("Blur is an affect now on mob", true, mob.getMobAffects().hasAffect("blur"));
    }
}
