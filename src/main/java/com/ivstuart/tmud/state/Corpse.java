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
import com.ivstuart.tmud.person.carried.Inventory;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Corpse extends Item {

    private static final long serialVersionUID = -1665651693089947124L;

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String[] periodOfTime = {"very recently",
            "recently",
            "a short while ago",
            "a while ago",
            "ages ago"};
    protected Inventory _inventory;
    private String whoKilledMe;
    private long whenKilled;

    public Corpse() {
    }

    public Corpse(Mob mob_) {

        LOGGER.debug("Creating corpse for mob id = " + mob_.getId());

        List<Equipable> eq = mob_.getEquipment().removeAll();

        _inventory = new Inventory(mob_.getInventory());

        mob_.getInventory().clear();

        for (Equipable item : eq) {
            _inventory.add((Item) item);
        }

        SomeMoney money = new Money(Money.COPPER, mob_.getCopper());

        _inventory.getPurse().add(money);

    }

    public void setWhoKilledMe(String whoKilledMe) {
        this.whoKilledMe = whoKilledMe;
    }

    public Inventory getInventory() {
        if (_inventory == null) {
            _inventory = new Inventory();
        }
        return _inventory;
    }

    @Override
    public String getLook() {

        if (_inventory == null || _inventory.isEmpty()) {
            return super.toString();
        } else {
            return super.toString() + "\n" + _inventory.toString();
        }

    }

    @Override
    public boolean isCorpse() {
        return true;
    }

    public String investigation() {

        return "Killed by " + whoKilledMe + " , " + getWhenKilled();
    }

    private String getWhenKilled() {

        int index = (int) (System.currentTimeMillis() - whenKilled) / (30 * 1000);
        if (index >= periodOfTime.length) {
            index = periodOfTime.length - 1;
        }
        return periodOfTime[index];

    }

    public void setWhenKilled(long whenKilled) {
        this.whenKilled = whenKilled;
    }
}
