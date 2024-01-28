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

package com.ivstuart.tmud.constants;

public enum DoorState {
    OPEN("(", ")"),
    CLOSED("[", "]"),
    LOCKED("{", "}"),
    BOLTED("-", "-"),
    BROKEN("(", "]");

    private final String start;
    private final String end;

    DoorState(String begin, String end) {
        this.start = begin;
        this.end = end;
    }

    public String getBegin() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public boolean isScanable() {
        return (this == DoorState.OPEN || this == DoorState.BROKEN);
    }

}
