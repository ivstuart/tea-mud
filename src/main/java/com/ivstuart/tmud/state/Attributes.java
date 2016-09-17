/*
 *  Copyright 2016. Ivan Stuart
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

package com.ivstuart.tmud.state;

import java.io.Serializable;

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

    @Override
    public String toString() {
        return "Attributes{" +
                "_strength=" + _strength +
                ", _consitution=" + _consitution +
                ", _intelligence=" + _intelligence +
                ", _dexterity=" + _dexterity +
                ", _wisdom=" + _wisdom +
                '}';
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
