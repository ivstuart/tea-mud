/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 09-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.person.carried;

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Torch;
import com.ivstuart.tmud.utils.MudArrayList;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author stuarti
 */
public class Inventory implements Serializable {

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
                if (keyId.equals(item.getProperties())) {
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
        }

        return sb.toString();
    }

    public SomeMoney getPurse() {
        return purse;
    }

    public void setPurse(SomeMoney purse) {
        this.purse = purse;
    }

    public String getPurseString() {
        return purse.toString();
    }

    public boolean hasLighter() {
        for (Item item : items) {
            if (item.getType() != null) {
                if ("LIGHTER".indexOf(item.getType()) > -1) {
                    return true;
                }
            }

        }
        return false;
    }

    public boolean hasLockpicks() {
        for (Item item : items) {
            if (item.getType() != null) {
                if ("lockpicks".indexOf(item.getType()) > -1) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasBoat() {
        for (Item item : items) {
            if (item.isBoat()) {
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

    @Override
    public String toString() {
        return "Inventory{" +
                "items=" + items +
                ", purse=" + purse +
                '}';
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

    public MudArrayList<Item> getItems() {
        return items;
    }

    public boolean hasMoney(Money money) {
        return this.getPurse().getValue() >= money.getValue();
    }

    /**
     * get all from corpse
     * get coins from corpse
     * get all gold from corpse
     * get silver corpse
     *
     * @param input
     * @return
     */
    public SomeMoney removeCoins(String input) {
        int type = -1;
        boolean allCoins = false;
        if (input.indexOf("all") > -1) {
            type = Money.COPPER;
        }
        if (input.indexOf("coins") > -1) {
            type = Money.COPPER;
        }
        if (input.indexOf("copper") > -1) {
            type = Money.COPPER;
        }
        if (input.indexOf("silver") > -1) {
            type = Money.SILVER;
        }
        if (input.indexOf("gold") > -1) {
            type = Money.GOLD;
        }
        if (input.indexOf("plati") > -1) {
            type = Money.PLATINUM;
        }
        if (type > -1) {
            String inputSplit[] = input.split(" ");
            if (inputSplit == null) {
                return null;
            }
            int coins = 0;
            try {
                coins = Integer.parseInt(inputSplit[0]);
            } catch (NumberFormatException e) {
                if (inputSplit[0].equals("all")) {
                    allCoins = true;
                } else {
                    return null;
                }
            }

            if (allCoins) {
                if (purse == null || purse.isEmpty()) {
                    return null;
                }
                SomeMoney someMoney = new MoneyBag(purse);
                purse.clear();
                return someMoney;
            }

            Money cash = new Money(type, coins);

            if (purse.remove(cash)) {
                return cash;
            }
        }
        return null;
    }

    public void addAll(Inventory inventory) {
        items.addAll(inventory.getItems());
        purse.add(inventory.getPurse());
    }

    public int getWeight() {
        int grams = purse.getWeight();

        for (Item item : items) {
            grams += item.getWeight();
        }

        return grams;
    }
}
