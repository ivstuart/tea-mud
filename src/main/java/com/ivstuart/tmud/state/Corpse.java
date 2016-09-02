package com.ivstuart.tmud.state;

import java.util.List;

import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ivstuart.tmud.common.Equipable;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.person.carried.Inventory;

public class Corpse extends Item {

	private static final long serialVersionUID = -1665651693089947124L;

	private static final Logger LOGGER = LogManager.getLogger();

	protected Inventory _inventory;
	
	private String whoKilledMe;
	
	private String whenIwasKilled;

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

        return "Killed by "+whoKilledMe+" , "+whenIwasKilled;
	}
}
