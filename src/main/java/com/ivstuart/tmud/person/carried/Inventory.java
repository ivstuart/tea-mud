/*
 * Created on 09-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.person.carried;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Torch;
import com.ivstuart.tmud.utils.MudArrayList;

/**
 * @author stuarti
 * 
 */
public class Inventory implements Serializable {

	// TODO - Weight allowance for what can be carried?

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MudArrayList<Item> items;

	private SomeMoney purse;

	/**
	 * 
	 */
	public Inventory() {
		super();
		items = new MudArrayList<Item>(true);
		purse = new MoneyBag();
	}

	public Inventory(Inventory inventory) {
		super();
		items = new MudArrayList<Item>(inventory.items);
		purse = new MoneyBag(inventory.purse);
	}

	public void add(Item thing) {
		items.add(thing);
	}

	public void add(SomeMoney cash) {
		purse.add(cash);
	}

	public void addAll(Collection<Item> itemCollection) {
		items.addAll(itemCollection);
	}

	public void clear() {
		items.clear();
		purse.clear();
	}

	public boolean containsKey(String keyId) {
		for (Item item : items) {
			if (item.isKey()) {
				if (item.getId().equals(keyId)) {
					return true;
				}
			}
		}
		return false;
	}

	public Item get(String name_) {
		return items.get(name_);
	}

	public String getInfo() {
		StringBuilder sb = new StringBuilder();

		sb.append(purse.toString());

		for (Item item : items) {
			sb.append(item.getBrief() + "\n");
			// TODO debug an issue
			// sb.append(" Alias is "+item.getAlias() + "\n");
		}

		return sb.toString();
	}

	public SomeMoney getPurse() {
		return purse;
	}

	public String getPurseString() {
		return purse.toString();
	}

	public boolean hasLighter() {
		for (Item item : items) {
			if ("LIGHTER".indexOf(item.getType()) > -1) {
				return true;
			}

		}
		return false;
	}

	public boolean hasLightSource() {
		for (Item item : items) {
			if (item.isTorch()) {
				Torch torch = (Torch) item;
				if (torch.isLit()) {
					return true;
				}
			}

		}
		return false;
	}

	public boolean isEmpty() {

		if (purse == null && items == null) {
			return true;
		} else if (items != null && !items.isEmpty()) {
			return false;
		} else if (purse != null && !purse.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

	public boolean remove(Item item_) {
		return items.remove(item_);
	}

	public Item remove(String name) {
		return items.remove(name);
	}

	public boolean hasSharpEdge() {
		for (Item item : items) {
			if ("SHARP".indexOf(item.getType()) > -1) {
				return true;
			}

		}
		return false;
	}

	public Iterator<Item> iterator() {
		return items.iterator();
	}
}
