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


package com.ivstuart.tmud.person.carried;

import com.ivstuart.tmud.common.Equipable;
import com.ivstuart.tmud.constants.DamageType;
import com.ivstuart.tmud.constants.EquipLocation;
import com.ivstuart.tmud.person.statistics.affects.Affect;
import com.ivstuart.tmud.state.items.*;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.utils.MudArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.ivstuart.tmud.constants.EquipLocation.*;
import static com.ivstuart.tmud.constants.SpellNames.PROTECTION;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Equipment implements Serializable {

    private static final long serialVersionUID = -4733577084794956703L;
    private static final Logger LOGGER = LogManager.getLogger();
    private final MudArrayList<Equipable> _equipment;
    // Used to work out if we have space at that location to put on said item.
    private final int[] _slots = new int[EquipLocation.values().length];
    private final Mob mob;
    /* Special slots */
    private Equipable _primary;
    private Equipable _secondary;
    private Equipable _both;
    private Equipable _natural;
    private int kickBonus;

    public Equipment(Mob mob) {
        this.mob = mob;
        _equipment = new MudArrayList<>();
    }

    public int getKickBonus() {
        return kickBonus;
    }

    public void increaseKick(int kickBonus) {
        this.kickBonus += kickBonus;
    }

    public boolean add(Equipable eq) {
        if (this.equip(eq)) {
            sortEquipment();
            return true;
        }
        return false;
    }


    @SuppressWarnings("unchecked")
    private void sortEquipment() {
        Collections.sort(_equipment);
    }

    private boolean checkTwoHandedAndNotEmptyHands(Integer location) {
        if (location != BOTH.ordinal()) {
            return false;
        }
        if (_slots[PRIMARY.ordinal()] > 0
                || _slots[SECONDARY.ordinal()] > 0) {
            LOGGER.debug("For two handed weapon one of the slots is taken so can not use both hands");
            return true;
        }
        return false;
    }

    public void clear() {
        _equipment.clear();
        _primary = null;

        _secondary = null;
        _both = null;
        _natural = null;

        Arrays.fill(_slots, 0);

    }

    /**
     * public boolean add(Equipable item) { if (this.equip(item)) {
     * item.equip(me.getStats()); return true; } return false; }
     */

    private boolean equip(Equipable item) {

        for (Integer location : item.getWear()) {
            if (_slots[location] < EquipLocation.getCapacity(location)) {

                if (checkTwoHandedAndNotEmptyHands(location)) {
                    continue;
                }
                item.setWorn(location);
                _slots[location]++;
                _equipment.add(item);
                if (location == PRIMARY.ordinal()) {
                    _primary = item;
                } else if (location == SECONDARY.ordinal()) {
                    _secondary = item;
                } else if (location == BOTH.ordinal()) {
                    _both = item;
                }
                return true;
            }

        }

        return false;

    }

    public Equipable get(String item_) {
        return _equipment.get(item_);
    }

    public Equipable getPrimary() {
        if (_both != null) {
            return _both;
        }
        if (_primary != null) {
            return _primary;
        }
        /*
         * Should not return this if (_secondary != null) { return _secondary; }
         */
        return _natural;
    }

    public Equipable getSecondary() {
        return _secondary;
    }

    public Armour getTotalArmour() {

        // Need to factor in any damage to armour.
        // Would it be better to add and remove armour on an item per item
        // basis?
        Armour totalArmour = new Armour();
        for (Equipable eq : _equipment) {
            // @Deprecated
            if (eq instanceof Armour) {
                totalArmour.add((Armour) eq);
            }
            if (eq instanceof BasicArmour) {
                totalArmour.add((BasicArmour) eq);
            }
        }

        Armour skin = new Armour(mob.getRace().getArmour());
        totalArmour.add(skin);

        Affect armourBuff = mob.getMobAffects().getAffect(PROTECTION);

        int buff;
        if (armourBuff != null) {
            buff = armourBuff.getAmount();
            Armour protection = new Armour();
            protection.setArmourBuff(buff);
            totalArmour.add(protection);
        }

        return totalArmour;
    }

    public Weapon getWeapon() {
        if (_both != null && _both instanceof Weapon) {
            return (Weapon) _both;
        } else if (_primary != null && _primary instanceof Weapon) {
            return (Weapon) _primary;
        } else if (_secondary != null && _secondary instanceof Weapon) {
            return (Weapon) _secondary;
        }
        return (Weapon) _natural;
    }

    public Equipable remove(String item) {

        Equipable eq = _equipment.remove(item);

        if (eq == null) {
            EquipLocation el = EquipLocation.valueOf(item.toUpperCase());

            // Maybe need to replace Equipable with Item
            for (Equipable eqItem : _equipment) {
                if (eqItem.getWorn() == el.ordinal()) {
                    eq = eqItem;
                    _equipment.remove(eq);
                    break;
                }

            }

            if (eq == null) {
                return null;
            }
        }

        _slots[eq.getWorn()]--;

        /* to remove special slots */
        if (eq == _primary) {
            _primary = null;
        } else if (eq == _secondary) {
            _secondary = null;
        } else if (eq == _both) {
            _both = null;
        }

        return eq;
    }

    public List<Equipable> removeAll() {
        List<Equipable> eqList = new ArrayList<>(_equipment);

        this.clear();
        return eqList;
    }

    public void swapHands() {
        if (_primary != null || _secondary != null) {
            Equipable temp = _primary;
            _primary = _secondary;
            _secondary = temp;
            if (_primary != null) {
                _primary.setWorn(PRIMARY.ordinal());
                _slots[PRIMARY.ordinal()] = 1;
            } else {
                _slots[PRIMARY.ordinal()] = 0;
            }
            if (_secondary != null) {
                _secondary.setWorn(SECONDARY.ordinal());
                _slots[SECONDARY.ordinal()] = 1;
            } else {
                _slots[SECONDARY.ordinal()] = 0;
            }

        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("You have equipped:\n");

        for (Equipable eq : _equipment) {
            sb.append("<");
            sb.append(EquipLocation.values()[eq.getWorn()].getDesc());
            sb.append("> ");
            // sb.append(" slots: ["+_slots[eq.getWorn()]+"] ");
            Item item;
            if (eq instanceof Item) {
                item = (Item) eq;
                sb.append(item.getBrief());
            } else {
                sb.append(eq);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean hasShieldEquiped() {
        if (_primary != null && ((Item) _primary).isShield()) {
            return true;
        }
        return _secondary != null && ((Item) _secondary).isShield();
    }

    public int getAPB() {
        int apb = 0;
        for (Equipable eq : _equipment) {
            apb += eq.getAPB();
        }

        return apb;
    }

    public int getWeight() {
        int grams = 0;
        for (Equipable eq : _equipment) {
            Item item = (Item) eq;
            grams += item.getWeight();
        }

        return grams;
    }

    public boolean hasClimbingBoots() {
        if (_slots[FEET.ordinal()] == 0) {
            return false;
        }

        for (Equipable eq : _equipment) {
            Item item = (Item) eq;
            if (item.getWorn() == FEET.ordinal()) {
                return item.hasItemEnum(ItemEnum.CLIMBING);
            }
        }

        return false;
    }

    public int getSave(DamageType damageType) {
        int total = 0;
        for (Equipable eq : _equipment) {
            Item item = (Item) eq;
            if (damageType.equals(item.getSaveType())) {
                total += item.getSave();
            }
        }
        return total;
    }

    public Equipable getRandom() {
        int index = (int) (Math.random() * EquipLocation.values().length);
        for (Equipable eq : _equipment) {
            Item item = (Item) eq;
            if (item.getWorn() == index) {
                return eq;
            }
        }
        return null;
    }

    public boolean remove(Equipable eq) {
        _slots[eq.getWorn()]--;

        if (eq == _primary) {
            _primary = null;
        } else if (eq == _secondary) {
            _secondary = null;
        } else if (eq == _both) {
            _both = null;
        }

        return _equipment.remove(eq);
    }

    public int getHitRollBonus() {
        int total = 0;
        for (Equipable eq : _equipment) {
            Item item = (Item) eq;
            total += item.getHitRoll();
        }
        return total;
    }

    public int getDamageRollBonus() {
        int total = 0;
        for (Equipable eq : _equipment) {
            Item item = (Item) eq;
            total += item.getDamageRoll();
        }
        return total;
    }

    public boolean hasBelt() {
        return _slots[WAIST.ordinal()] > 0;
    }

    public boolean hasThruBeltSlots() {
        return _slots[BELT.ordinal()] < BELT.getCapacity();
    }

    public void sheath(Item item) {
        _slots[item.getWorn()]--;
        item.setWorn(BELT.ordinal());
        _slots[item.getWorn()]++;

        if (item == _primary) {
            _primary = null;
        } else if (item == _secondary) {
            _secondary = null;
        } else if (item == _both) {
            _both = null;
        }
    }

    public boolean draw(String input) {

        Equipable eq = _equipment.get(input);

        if (eq == null) {
            return false;
        }

        if (eq.getWorn() != BELT.ordinal()) {
            return false;
        }
        this.remove(input);

        if (equip(eq)) {
            return true;
        } else {
            _equipment.add(eq);
        }
        return false;
    }

    public boolean hasBothHandsFull() {
        if (_both != null) {
            return true;
        }
        return _primary != null && _secondary != null;
    }

    public boolean hasSharpEdge() {
        for (Equipable eq : _equipment) {
            Item item = (Item) eq;

            if (item != null && item.getType().contains("SHARP")) {
                return true;
            }

        }
        return false;

    }
}
