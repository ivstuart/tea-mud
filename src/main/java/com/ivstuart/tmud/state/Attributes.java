package com.ivstuart.tmud.state;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Attributes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Attribute _strength;
    private Attribute _consitution;
    private Attribute _intelligence;
    private Attribute _dexterity;
    private Attribute _wisdom;

    public Attributes(int[] values) {
        _strength = new Attribute("Strength", values[0]);
        _consitution = new Attribute("Consitution", values[1]);
        _intelligence = new Attribute("Intelligence", values[2]);
        _dexterity = new Attribute("Dexterity", values[3]);
        _wisdom = new Attribute("Wisdom", values[4]);
    }

    public Attribute getCON() {
        return _consitution;
    }

    public Attribute getDEX() {
        return _dexterity;
    }

    public Attribute getINT() {
        return _intelligence;
    }

    public Attribute getSTR() {
        return _strength;
    }

    public Attribute getWIS() {
        return _wisdom;
    }

    @Override
    public final String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Attribute get(String stat) {
        switch (stat.toUpperCase()) {
            case "STR":
                return getSTR();
            case "CON":
                return getCON();
            case "DEX":
                return getDEX();
            case "WIS":
                return getWIS();
            case "INT":
                return getINT();
            default:
                return getSTR();
        }
    }

    public int getTotal() {
        return _consitution.getValue() +
                _dexterity.getValue() +
                _intelligence.getValue() +
                _strength.getValue() +
                _wisdom.getValue();
    }
}
