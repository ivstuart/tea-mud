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

package com.ivstuart.tmud.poc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class World {
    public static final Zone zone = new Zone(100, 160); // 40 by 40 works well.
    private static final Logger LOGGER = LogManager.getLogger();

    private static Map<GridLocation, Room> roomMap = new HashMap<>(100);
    private static int counter = 0;
    private static Room selectedRoom;

    public static Room getRoom(GridLocation mapLocation) {
        return roomMap.get(mapLocation);
    }

    public static void addRoom(Room room) {


        if (room.getGridLocation().isOutsideOfZone(zone)) {
            LOGGER.info("Hit edge of zone adding room");
            return;
        }

        counter++;
        room.setRoomNumber(counter);
        roomMap.put(room.getGridLocation(), room);
        if (selectedRoom == null) {
            selectedRoom = room;
        }
    }

    public static Map<GridLocation, Room> getRoomMap() {
        return roomMap;
    }

    public static void setRoomMap(Map<GridLocation, Room> loadedObject) {
        roomMap = loadedObject;
    }

    public static Room getRandomRoom(Random random) {
        if (roomMap.isEmpty()) {
            return null;
        }
        Object[] rooms = roomMap.values().toArray();
        return (Room) rooms[random.nextInt(rooms.length)];
    }

    public static Room getSelectedRoom() {
        return selectedRoom;
    }

    public static void setRoomSelected(int x, int y, int z) {

        Room room = World.getRoom(new GridLocation(x, y, z));
        if (room != null) {
            selectedRoom = room;

            JInputOutputPanel.setGridLocation(room);
            JBitsPanel.setValues(room.getRoomFlags().getBitSet());

        }
    }

    public static void removeRoom(Room room) {
        roomMap.remove(room.getGridLocation());
    }
}
