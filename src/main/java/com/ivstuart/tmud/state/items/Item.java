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

package com.ivstuart.tmud.state.items;

import com.ivstuart.tmud.common.*;
import com.ivstuart.tmud.constants.DamageType;
import com.ivstuart.tmud.constants.EquipLocation;
import com.ivstuart.tmud.constants.EquipmentConstants;
import com.ivstuart.tmud.constants.Profession;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.person.statistics.diseases.Disease;
import com.ivstuart.tmud.state.mobs.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class Item extends Prop implements Equipable, Msgable {

    private static final long serialVersionUID = 2149506293102292040L;

    private static final Logger LOGGER = LogManager.getLogger();

    private final EnumSet<ItemEnum> enumItemSet;

    protected String action;

    protected String type;

    protected String effects;

    protected List<Integer> wear;

    // in grams
    protected int weight;
    // copper
    protected int cost;
    protected SomeMoney someMoneyCost;
    // copper
    protected int rent;
    protected int worn;
    protected int size;
    protected int damagedPercentage;
    protected int apb;
    int loadPercentage = 1;
    private List<Profession> antiProfession;
    private String roomId;
    // Consider wrap the pair this in a class
    private int hitRoll;
    private int damageRoll;
    private DamageType saveType;
    private int save;
    private int hp;
    private int mana;
    private int move;
    private Disease disease;

    public Item() {
        enumItemSet = EnumSet.noneOf(ItemEnum.class);
    }

    public boolean hasItemEnum(ItemEnum itemEnum) {
        return enumItemSet.contains(itemEnum);
    }

    public void setItemEnum(ItemEnum itemEnum) {
        enumItemSet.add(itemEnum);
    }

    // TODO add a copy constructor for Item after Item Flags enum integrated with code

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getHitRoll() {
        return hitRoll;
    }

    public void setHitRoll(int hitRoll) {
        this.hitRoll = hitRoll;
    }

    public int getDamageRoll() {
        return damageRoll;
    }

    public void setDamageRoll(int damageRoll) {
        this.damageRoll = damageRoll;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public DamageType getSaveType() {
        return saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = DamageType.valueOf(saveType);
    }

    public int getSave() {
        return save;
    }

    public void setSave(int save) {
        this.save = save;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public boolean isAntiProfession(Profession profession) {
        if (antiProfession == null) {
            return false;
        }
        return antiProfession.contains(profession);
    }

    public void setAntiProfession(String antiProfession) {
        if (this.antiProfession == null) {
            this.antiProfession = new ArrayList<>(4);
        }
        this.antiProfession.add(Profession.valueOf(antiProfession));
    }

    @Override
    public String toString() {
        return "Item{" +
                "_action='" + action + '\'' +
                ", _type='" + type + '\'' +
                ", _effects='" + effects + '\'' +
                ", _wear=" + wear +
                ", _weight=" + weight +
                ", _cost=" + cost +
                ", _someMoneyCost=" + someMoneyCost +
                ", _rent=" + rent +
                ", _worn=" + worn +
                ", _size=" + size +
                ", _damagedPercentage=" + damagedPercentage +
                ", apb=" + apb +
                ", loadPercentage=" + loadPercentage +
                ", antiProfession=" + antiProfession +
                ", roomId='" + roomId + '\'' +
                ", hitRoll=" + hitRoll +
                ", damageRoll=" + damageRoll +
                ", saveType=" + saveType +
                ", save=" + save +
                ", hp=" + hp +
                ", mana=" + mana +
                ", move=" + move +
                ", disease=" + disease +
                '}';
    }

    public int getAPB() {
        return apb;
    }

    public void setAPB(int apb) {
        this.apb = apb;
    }

    @Override
    public void increaseDamage() {
        this.damagedPercentage++;
        if (damagedPercentage > 99) {
            damagedPercentage = 100;
        }
    }

    @Override
    public int getDamagedPercentage() {
        return damagedPercentage;
    }

    public void setDamagedPercentage(int damagedPercentage) {
        this.damagedPercentage = damagedPercentage;
    }

    @Override
    public int compareTo(Object o) {
        Item item = (Item) o;
        if (worn == item.worn) {
            return 0;
        }
        if (worn < item.worn) {
            return -1;
        }
        return 1;
    }

    @Override
    public void equip(Mob mob) {

        mob.getHp().increaseCurrentAndMaximum(hp);
        mob.getMv().increaseCurrentAndMaximum(move);
        mob.getMana().increaseCurrentAndMaximum(mana);

    }

    @Override
    public void unequip(Mob mob) {
        mob.getHp().increaseCurrentAndMaximum(-hp);
        mob.getMv().increaseCurrentAndMaximum(-move);
        mob.getMana().increaseCurrentAndMaximum(-mana);

    }

    @Override
    public Gender getGender() {

        return null;
    }

    @Override
    public String getName() {

        return this.getId();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size_) {
        size = size_;
    }

    public String getType() {
        return type;
    }

    public void setType(String type_) {
        this.type = type_;
    }

    @Override
    public List<Integer> getWear() {
        if (wear == null) {
            return Collections.emptyList();
        }
        return wear;
    }

    // HEAD NECK for a scarf
    public void setWear(String wear_) {
        this.wear = new ArrayList<>(1);

        for (String loc : wear_.split(" ")) {
            EquipLocation eqLoc = EquipLocation.valueOf(loc);
            if (eqLoc != null) {
                wear.add(eqLoc.ordinal());
            } else {
                LOGGER.warn("Item location = " + loc + " not known!");
            }
        }
    }

    @Override
    public int getWorn() {
        return worn;
    }

    public boolean isKey() {
        return false;
    }

    public boolean isTorch() {
        return false;
    }

    @Override
    public void out(Msg message) {

    }

    public void setAction(String action_) {
        this.action = action_;
    }

    public void setEffects(String effects_) {
        this.effects = effects_;
    }

    public void setRent(int rent_) {
        this.rent = rent_;
    }

    @Override
    public boolean setWorn(int worn_) {
        worn = worn_;
        return true;
    }


    public boolean isButcherable() {
        if(type == null) {
            return false;
        }
        return (type.contains("BUTCHERABLE"));
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight_) {
        this.weight = weight_;
    }

    public boolean isCorpse() {
        return false;
    }

    public boolean isShield() {
        if (type == null) {
            return false;
        }
        return (type.contains("SHIELD"));
    }

    public boolean isRecitable() {
        return false;
    }

    public boolean isContainer() {
        return false;
    }

    public SomeMoney getCost() {
        return someMoneyCost;

    }

    public void setCost(int cost_) {
        this.cost = cost_;
        someMoneyCost = new Money(Money.COPPER, cost);
    }

    public void setLoadPercentage(int loadPercentage) {
        this.loadPercentage = loadPercentage;
    }

    public boolean isLoaded() {
        return DiceRoll.ONE_D100.rollLessThanOrEqualTo(loadPercentage);
    }

    public void setInvisible(boolean flag) {
        if (!hasItemEnum(ItemEnum.NO_INVISIBLE)) {
            super.setInvisible(flag);
        }
    }

    @Override
    public String look() {

        int indexCondition = ((EquipmentConstants.condition.length - 1) * getDamagedPercentage()) / 100;
        return this.getBrief() + " " + EquipmentConstants.condition[indexCondition];
    }

    public boolean isCorrectSize(int mobSize) {
        if (this instanceof Armour) {
            return ((double) Math.abs(size - mobSize) / mobSize) <= 0.1;
        }
        return true;
    }

    public void removeItemEnum(ItemEnum itemEnum) {
        enumItemSet.remove(itemEnum);
    }
}
