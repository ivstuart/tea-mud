package com.ivstuart.tmud.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ivstuart.tmud.common.Equipable;
import com.ivstuart.tmud.common.Gender;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.common.Msgable;
import com.ivstuart.tmud.constants.EquipLocation;

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

	public Item() {
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
		// TODO Auto-generated method stub

	}

	@Override
	public Gender getGender() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {

		return this.getId();
	}

	public int getSize() {
		return _size;
	}

	public String getType() {
		return _type;
	}

	@Override
	public List<Integer> getWear() {
		if (_wear == null) {
			return Collections.emptyList();
		}
		return _wear;
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

	public void setCost(int cost_) {
		this._cost = cost_;
		_someMoneyCost = new Money(Money.COPPER,_cost);
	}

	public void setEffects(String effects_) {
		this._effects = effects_;
	}

	public void setRent(int rent_) {
		this._rent = rent_;
	}

	public void setSize(int size_) {
		_size = size_;
	}

	public void setType(String type_) {
		this._type = type_;
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

	public void setWeight(int weight_) {
		this._weight = weight_;
	}

	@Override
	public boolean setWorn(int worn_) {
		_worn = worn_;
		return true;
	}

	@Override
	public void unequip(Mob mob) {
		// TODO Auto-generated method stub

	}

	public boolean isButcherable() {
		return (_type.indexOf("BUTCHERABLE") != -1);
	}

	public int getWeight() {
		return _weight;
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

	public int getDamagedPercentage() {
		return _damagedPercentage;
	}

	public void setDamagedPercentage(int damagedPercentage) {
		this._damagedPercentage = damagedPercentage;
	}
}
