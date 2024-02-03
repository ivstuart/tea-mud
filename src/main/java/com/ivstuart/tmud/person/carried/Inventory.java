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

import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.items.Torch;
import com.ivstuart.tmud.utils.MudArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author stuarti
 */
public class Inventory implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger();
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final MudArrayList<Item> items;

    private SomeMoney purse;

    /**
     *
     */
    public Inventory() {
        super();
        items = new MudArrayList<>(true);
        purse = new MoneyBag();
    }

    public Inventory(Inventory inventory) {
        super();
        items = new MudArrayList<>(inventory.items);
        purse = new MoneyBag(inventory.purse);
    }

    public void add(Item thing) {
        if (thing == null) {
            LOGGER.error("Trying to add null into inventory!");
            throw new IllegalArgumentException();
        }
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

    public String look() {
        StringBuilder sb = new StringBuilder();

        sb.append(purse.toString());

        for (Item item : items) {
            sb.append(item.getBrief()).append("\n");
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
                if ("LIGHTER".contains(item.getType())) {
                    return true;
                }
            }

        }
        return false;
    }

    public boolean hasLockpicks() {
        for (Item item : items) {
            if (item.getType() != null) {
                if ("lockpicks".contains(item.getType())) {
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
        } else return purse == null || purse.isEmpty();

    }

    public boolean remove(Item item_) {
        return items.remove(item_);
    }

    public Item remove(String name) {
        return items.remove(name);
    }

    public boolean hasSharpEdge() {
        for (Item item : items) {
            if (item.getType().contains("SHARP")) {
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
        if (input.contains("all")) {
            type = Money.COPPER;
        }
        if (input.contains("coins")) {
            type = Money.COPPER;
        }
        if (input.contains("copper")) {
            type = Money.COPPER;
        }
        if (input.contains("silver")) {
            type = Money.SILVER;
        }
        if (input.contains("gold")) {
            type = Money.GOLD;
        }
        if (input.contains("plati")) {
            type = Money.PLATINUM;
        }
        if (type > -1) {
            String[] inputSplit = input.split(" ");
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
