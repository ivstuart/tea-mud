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

package com.ivstuart.tmud.common;

public enum Gender {

    MALE("him", "his", "he"), FEMALE("her", "her", "she"), NEUTRAL("it", "it",
            "it");

    private final String m;
    private final String s;
    private final String e;

    Gender(String m, String s, String e) {
        this.m = m;
        this.s = s;
        this.e = e;
    }

    public String getE() {
        return e;
    }

    public String getM() {
        return m;
    }

    public String getS() {
        return s;
    }

    public boolean isFemale() {
        return this == Gender.FEMALE;
    }

    public Gender swap() {
        if (this == Gender.FEMALE) {
            return Gender.MALE;
        } else if (this == Gender.MALE) {
            return Gender.FEMALE;
        }
        return Gender.NEUTRAL;

    }
}
