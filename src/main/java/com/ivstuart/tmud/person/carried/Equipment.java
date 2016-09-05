/*
 * Created on 09-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.person.carried;

import com.ivstuart.tmud.common.Equipable;
import com.ivstuart.tmud.constants.DamageType;
import com.ivstuart.tmud.constants.EquipLocation;
import com.ivstuart.tmud.constants.EquipmentConstants;
import com.ivstuart.tmud.person.statistics.Affect;
import com.ivstuart.tmud.state.Armour;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Weapon;
import com.ivstuart.tmud.utils.MudArrayList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.ivstuart.tmud.constants.EquipLocation.FEET;
import static com.ivstuart.tmud.constants.SpellNames.PROTECTION;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Equipment implements Serializable {

	private static final long serialVersionUID = -4733577084794956703L;

	private MudArrayList<Equipable> _equipment;

	/* Special slots */
	private Equipable _primary;

	private Equipable _secondary;

	private Equipable _both;

	private Equipable _natural;

	// Used to work out if we have space at that location to put on said item.
	private int _slots[] = new int[EquipmentConstants.location.length];

	private Mob mob;

	public Equipment(Mob mob) {
		this.mob = mob;
		_equipment = new MudArrayList<Equipable>();
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
		if (location != EquipmentConstants.TWO_HANDED) {
			return false;
		}
		if (_slots[EquipmentConstants.PRIMARY] > 0
				|| _slots[EquipmentConstants.SECONDARY] > 0) {
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
			if (_slots[location] < EquipmentConstants.locationLimits[location]) {

				if (checkTwoHandedAndNotEmptyHands(location)) {
					continue;
				}
				item.setWorn(location);
				_slots[location]++;
				_equipment.add(item);
				if (location == EquipmentConstants.PRIMARY) {
					_primary = item;
				} else if (location == EquipmentConstants.PRIMARY) {
					_secondary = item;
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
			if (eq instanceof Armour) {
				((Armour) eq).add(totalArmour);
			}
		}

		Armour skin = new Armour(mob.getRace().getArmour());
		totalArmour.add(skin);

		Affect armourBuff = mob.getMobAffects().getAffect(PROTECTION);

		int buff = 0;
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
			if (el == null) {
				return null;
			}
			// Maybe need to replace Equipable with Item
			for (Equipable eqItem : _equipment) {
				if (((Item) eqItem).getWorn() == el.ordinal()) {
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
		List<Equipable> eqList = new ArrayList<Equipable>(_equipment);

		this.clear();
		return eqList;
	}

	public void swapHands() {
		if (_primary != null || _secondary != null) {
			Equipable temp = _primary;
			_primary = _secondary;
			_secondary = temp;
			if (_primary != null) {
				_primary.setWorn(EquipmentConstants.PRIMARY);
			}
			if (_secondary != null) {
				_secondary.setWorn(EquipmentConstants.SECONDARY);
			}

		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("You have equiped:\n");

		for (int index = 0; index < _equipment.size(); index++) {
			Equipable eq = _equipment.get(index);
			sb.append("<");
			sb.append(EquipmentConstants.location[eq.getWorn()]);
			sb.append("> ");
			Item item = null;
			if (eq instanceof Item) {
				item = (Item)eq;
				sb.append(item.getBrief());
			}
			else {
				sb.append(eq);
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public boolean hasShieldEquiped() {
		if (_primary != null && ((Item)_primary).isShield()) {
			return true;
		}
		if (_secondary != null && ((Item)_secondary).isShield()) {
			return true;
		}	
		return false; 
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
				return item.isClimbing();
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
}
