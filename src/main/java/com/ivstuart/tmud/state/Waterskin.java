package com.ivstuart.tmud.state;

import static com.ivstuart.tmud.state.LiquardType.WATER;

public class Waterskin extends Item {

    private static final long serialVersionUID = -4557556852795411856L;

    private int capacity = 4;
    private int drafts = 4;
    private LiquardType liquardType;

    public Waterskin() {

    }

    public LiquardType getLiquardType() {
        if (liquardType == null) {
            return WATER;
        }
        return liquardType;
    }

    public void setLiquardType(String liquardType) {
        String params[] = liquardType.split(":");
        this.liquardType = new LiquardType(
                Integer.parseInt(params[0]),
                Integer.parseInt(params[1]),
                Integer.parseInt(params[2]),
                Integer.parseInt(params[3]));
    }

    public void drink() {
        drafts--;
    }

    public void empty() {
        drafts = 0;
    }

    public void fill() {
        drafts = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity_) {
        capacity = capacity_;
    }

    public int getDrafts() {
        return drafts;
    }

    public void setDrafts(int drafts_) {
        drafts = drafts_;
    }

}
