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

import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.skills.Spell;
import com.ivstuart.tmud.world.WeatherSky;
import com.ivstuart.tmud.world.World;

public class ControlWeather implements SpellEffect {

    @Override
    public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

        // Note pass in parameter for better or worse weather
        WeatherSky sky = World.getWeather().getNextSky(1000);

        World.setWeather(sky);

        caster_.out("The weather changes");
        caster_.out(sky.getDesc());
    }

    public boolean isPositiveEffect() {
        return true;
    }

}
