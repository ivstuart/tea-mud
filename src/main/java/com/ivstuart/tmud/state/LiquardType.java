/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

import java.io.Serializable;

/**
 * Created by Ivan on 03/09/2016.
 */
public class LiquardType implements Serializable {

    public static final LiquardType WATER = new LiquardType(0, 0, 10, 100);
    private int poison;
    private int alcohol;
    private int food;
    private int thirst;

    public LiquardType(int poison, int alcohol, int food, int thirst) {
        this.poison = poison;
        this.alcohol = alcohol;
        this.food = food;
        this.thirst = thirst;
    }

    public int getPoison() {
        return poison;
    }

    public void setPoison(int poison) {
        this.poison = poison;
    }

    public int getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(int alcohol) {
        this.alcohol = alcohol;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getThirst() {
        return thirst;
    }

    public void setThirst(int thirst) {
        this.thirst = thirst;
    }

}
