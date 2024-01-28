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

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.common.Equipable;

import java.util.ArrayList;
import java.util.List;

public class GemList extends Item implements Equipable {

    private static final long serialVersionUID = -5149906568548224750L;

    protected List<Gem> gems;

    public GemList() {
        gems = new ArrayList<>(4);
    }

    @Override
    public void equip(Mob mob) {
        for (Gem gem : gems) {
            gem.equip(mob);
        }
    }

    public void setLevel(String level_) {
        String[] element = level_.split(" ");
        int index = 0;
        for (String levelString : element) {
            Gem gem = gems.get(index);
            gem.setLevel(levelString);
            index++;
        }

    }

    public void setMana(String mana_) {
        String[] element = mana_.split(" ");
        for (String manaString : element) {
            Gem gem = new Gem();
            gems.add(gem);
            gem.setMana(manaString);
        }
    }

    @Override
    public void unequip(Mob mob) {
        for (Gem gem : gems) {
            gem.unequip(mob);
        }
    }

}
