package com.ivstuart.tmud.state;

import com.ivstuart.tmud.common.*;
import com.ivstuart.tmud.constants.EquipLocation;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
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

	public Item() {
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
				'}';
	}

	public int getAPB() {
		return apb;
	}

	public void setAPB(int apb) {
		this.apb = apb;
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

	@Override
	public void unequip(Mob mob) {


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
		return (_type.indexOf("SHIELD") != -1);
	}

	public Room getRoom() {
		// TODO need for locate object spell
		return null;
	}

    public boolean isRecitable() {
    	return false;
    }

	public boolean isContainer() {
		return false;
	}

    public SomeMoney getCost() {
    	return _someMoneyCost;
    	// TODO not make a new object here in code. Do it in setter so once.

    }

	public void setCost(int cost_) {
		this._cost = cost_;
		_someMoneyCost = new Money(Money.COPPER, _cost);
	}

	public int getDamagedPercentage() {
		return _damagedPercentage;
	}

	public void setDamagedPercentage(int damagedPercentage) {
		this._damagedPercentage = damagedPercentage;
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
}
