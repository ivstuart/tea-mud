/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.person.carried.Inventory;

public class Bag extends Item {

    private static final long serialVersionUID = -8765750404257030003L;

    private Inventory inventory;

    public boolean isContainer() {
        return true;
    }

    public Inventory getInventory() {
        if (inventory == null) {
            inventory = new Inventory();
        }
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return "Bag{" +
                "inventory=" + inventory +
                '}';
    }
}
