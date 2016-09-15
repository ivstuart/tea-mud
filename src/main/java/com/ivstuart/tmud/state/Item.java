/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.common.*;
import com.ivstuart.tmud.constants.DamageType;
import com.ivstuart.tmud.constants.EquipLocation;
import com.ivstuart.tmud.constants.EquipmentConstants;
import com.ivstuart.tmud.constants.Profession;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.person.statistics.diseases.Disease;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Item extends Prop implements Equipable, Msgable {

	private static final long serialVersionUID = 2149506293102292040L;

	private static final Logger LOGGER = LogManager.getLogger();

	protected String _action;

	protected String _type;

	protected String _effects;

	protected List<Integer> _wear;

	// in grams
	protected int _weight;
	// copper
	protected int _cost;
	protected SomeMoney _someMoneyCost;
	// copper
	protected int _rent;
	protected int _worn;
	protected int _size;
	protected int _damagedPercentage;
	protected int apb;
	protected boolean isClimbing;
	protected boolean isShopSupplied;
	int loadPercentage = 1;
	private boolean isNoDrop;
	private boolean isNoRemove;
	private boolean isNoBank;
	private boolean isNoDonate;
	private boolean isNoInvisible;
	private List<Profession> antiProfession;
	private boolean isAntiGood;
	private boolean isAntiEvil;
	private boolean isBoat;
	private String roomId;
	private boolean magic;
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
	}

	public boolean isAntiGood() {
		return isAntiGood;
	}

	public void setAntiGood(boolean antiGood) {
		isAntiGood = antiGood;
	}

	public boolean isAntiEvil() {
		return isAntiEvil;
	}

	public void setAntiEvil(boolean antiEvil) {
		isAntiEvil = antiEvil;
	}

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

	public boolean isBoat() {
		return isBoat;
	}

	public void setBoat(boolean boat) {
		isBoat = boat;
	}

	public boolean isNoDonate() {
		return isNoDonate;
	}

	public void setNoDonate(boolean noDonate) {
		isNoDonate = noDonate;
	}

	public boolean isNoInvisible() {
		return isNoInvisible;
	}

	public void setNoInvisible(boolean noInvisible) {
		isNoInvisible = noInvisible;
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
				"_action='" + _action + '\'' +
				", _type='" + _type + '\'' +
				", _effects='" + _effects + '\'' +
				", _wear=" + _wear +
				", _weight=" + _weight +
				", _cost=" + _cost +
				", _someMoneyCost=" + _someMoneyCost +
				", _rent=" + _rent +
				", _worn=" + _worn +
				", _size=" + _size +
				", _damagedPercentage=" + _damagedPercentage +
				", apb=" + apb +
				", isClimbing=" + isClimbing +
				", isShopSupplied=" + isShopSupplied +
				", loadPercentage=" + loadPercentage +
				", isNoDrop=" + isNoDrop +
				", isNoRemove=" + isNoRemove +
				", isNoBank=" + isNoBank +
				", isNoDonate=" + isNoDonate +
				", isNoInvisible=" + isNoInvisible +
				", antiProfession=" + antiProfession +
                ", isAntiGood=" + isAntiGood +
                ", isAntiEvil=" + isAntiEvil +
                ", isBoat=" + isBoat +
                ", roomId='" + roomId + '\'' +
                ", magic=" + magic +
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

	public boolean isNoBank() {
		return isNoBank;
	}

	public void setNoBank(boolean noBank) {
		isNoBank = noBank;
	}

	public boolean isNoRemove() {
		return isNoRemove;
	}

	public void setNoRemove(boolean noRemove) {
		isNoRemove = noRemove;
	}

	public int getAPB() {
		return apb;
	}

	public void setAPB(int apb) {
		this.apb = apb;
	}

	@Override
	public void increaseDamage() {
		this._damagedPercentage++;
		if (_damagedPercentage > 99) {
			_damagedPercentage = 100;
		}
	}

	@Override
	public int getDamagedPercentage() {
		return _damagedPercentage;
	}

	public void setDamagedPercentage(int damagedPercentage) {
		this._damagedPercentage = damagedPercentage;
	}

	public boolean isClimbing() {
		return isClimbing;
	}

	public void setClimbing(boolean climbing) {
		isClimbing = climbing;
	}

	public boolean isShopSupplied() {
		return isShopSupplied;
	}

	public void setShopSupplied(boolean shopSupplied) {
		isShopSupplied = shopSupplied;
	}

	@Override
	public int compareTo(Object o) {
		Item item = (Item) o;
		if (_worn == item._worn) {
			return 0;
		}
		if (_worn < item._worn) {
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
		return _size;
	}

	public void setSize(int size_) {
		_size = size_;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type_) {
		this._type = type_;
	}

	@Override
	public List<Integer> getWear() {
		if (_wear == null) {
			return Collections.emptyList();
		}
		return _wear;
	}

	// HEAD NECK for a scarf
	public void setWear(String wear_) {
		this._wear = new ArrayList<Integer>(1);

		for (String loc : wear_.split(" ")) {
			EquipLocation eqLoc = EquipLocation.valueOf(loc);
			if (eqLoc != null) {
				_wear.add(eqLoc.ordinal());
			} else {
				LOGGER.warn("Item location = " + loc + " not known!");
			}
		}
	}

	@Override
	public int getWorn() {
		return _worn;
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
		this._action = action_;
	}

	public void setEffects(String effects_) {
		this._effects = effects_;
	}

	public void setRent(int rent_) {
		this._rent = rent_;
	}

	@Override
	public boolean setWorn(int worn_) {
		_worn = worn_;
		return true;
	}



	public boolean isButcherable() {
		return (_type.indexOf("BUTCHERABLE") != -1);
	}

	public int getWeight() {
		return _weight;
	}

	public void setWeight(int weight_) {
		this._weight = weight_;
	}

	public boolean isCorpse() {
		return false;
	}

	public boolean isShield() {
		if (_type == null) {
			return false;
		}
		return (_type.indexOf("SHIELD") != -1);
	}

    public boolean isRecitable() {
    	return false;
    }

	public boolean isContainer() {
		return false;
	}

    public SomeMoney getCost() {
    	return _someMoneyCost;

    }

	public void setCost(int cost_) {
		this._cost = cost_;
		_someMoneyCost = new Money(Money.COPPER, _cost);
	}

	public void setLoadPercentage(int loadPercentage) {
		this.loadPercentage = loadPercentage;
	}

	public boolean isLoaded() {
		return DiceRoll.ONE_D100.rollLessThanOrEqualTo(loadPercentage);
	}

	public boolean isNoDrop() {
		return isNoDrop;
	}

	public void setNoDrop(boolean noDrop) {
		isNoDrop = noDrop;
	}

	public void setInvisible(boolean flag) {
		if (isNoInvisible == false) {
			super.setInvisible(flag);
		}
	}

	public void setMagic(boolean magic) {
		this.magic = magic;
	}

	@Override
	public String look() {

		int indexCondition = (EquipmentConstants.condition.length * getDamagedPercentage()) / 100;
		return this.getBrief() + EquipmentConstants.condition[indexCondition];
	}
}
