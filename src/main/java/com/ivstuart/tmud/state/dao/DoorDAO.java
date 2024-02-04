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

package com.ivstuart.tmud.state.dao;

import com.ivstuart.tmud.state.places.Door;
import com.ivstuart.tmud.state.places.RoomLocation;

public class DoorDAO {

    private Door door;

    private RoomLocation roomLocation;

    private String exit;

    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public String getExit() {
        return exit;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

    public void setRoomLocation(RoomLocation roomLocation) {
        this.roomLocation = roomLocation;
    }

    @Override
    public String toString() {
        return "DoorDAO [door=" + door + ", room=" + roomLocation + ", exit=" + exit
                + "]";
    }

    public RoomLocation getRoomLocation() {
        return roomLocation;
    }
}
