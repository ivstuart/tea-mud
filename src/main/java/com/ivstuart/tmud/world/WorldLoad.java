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

package com.ivstuart.tmud.world;

import com.google.gson.Gson;
import com.ivstuart.tmud.poc.*;
import com.ivstuart.tmud.state.places.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static com.ivstuart.tmud.server.LaunchMud.loadMudServerProperties;

public class WorldLoad {
    private static final Logger LOGGER = LogManager.getLogger();

   // private static final String fileName = "/gson/map.gson";
    private static final String fileName = "./map.gson";
    public static void load() {

        /* POC code STARTS here */
        LOGGER.info("You choose to load this file: " + fileName);

        GsonIO gsonIO = new GsonIO();

        Object loadedObject;

        try {
            loadedObject = gsonIO.load(fileName, WorldMap.getRoomMap().getClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<GridLocation, Place> loadedMap;

        if (loadedObject instanceof Map) {
            loadedMap = (Map<GridLocation, Place>) loadedObject;
        } else {
            LOGGER.error("Problem loading not a Map");
            return;
        }
        /* POC code ENDS here */

        Gson gson = new Gson();

        for (Object value : loadedMap.values()) {
            LOGGER.debug("Room json value: " + value);

            Place room = gson.fromJson(value.toString(), Place.class);

            WorldMap.addRoom(room);
        }

        //LOGGER.debug("Debugging World: " + WorldMap.getRoomMap());

    }

    public static Map<RoomLocation, Room> mapWorld(Map<GridLocation, Place> source) {
        Map<RoomLocation, Room> destination = new HashMap<>();
        for (Place place : source.values()) {

            Room room = getRoom(place);

            destination.put(room.getRoomLocation(),room);
        }

        // Copy doors to exit other side
        for (Room room : destination.values()) {
            for (Exit exit : room.getExits()) {
                Door door = exit.getDoor();
                if (door != null) {
                    String oppositeDirection = Facing.getOpposite(exit.getName());
                    Exit oppositeExit = exit.getDestinationRoom().getExit(oppositeDirection);

                    if (oppositeExit != null) {
                        oppositeExit.setDoor(door);
                    }
                    else {
                        LOGGER.warn("Failed to copy door to opposite side");
                    }

                }
            }
        }

        return destination;
    }

    public static Room getRoom(Place place) {
        Room room = new Room(getRoomLocation(place.getGridLocation()));
        room.setZoneId(place.getZoneId());

        for(Path path : place.getExits()) {

            Exit exit = getExit(path);

            // TODO state any additional state on exit

            room.add(exit);
            // TODO copy exit flags

            if(path.isDoor()) {
                // TODO add a door in the correct way
                Door door = new Door();
                exit.setDoor(door);
            }

        }

        int counter=0;
        for (RoomEnum flag : EnumSet.allOf(RoomEnum.class)) {
            if (place.getRoomFlags().getFlag(counter)) {
                room.setFlag(flag);
            }
            counter++;
        }
        return room;
    }

    public static Exit getExit(Path path) {
        RoomLocation destinationRoomLocation = getRoomLocation(path.getDestination());
        return new Exit(path.getName(),destinationRoomLocation);
    }

    public static RoomLocation getRoomLocation(GridLocation gridLocation) {
        int x = gridLocation.getX();
        int y = gridLocation.getY();
        int z = gridLocation.getZ();
        return new RoomLocation(x,y,z);
    }

    public static void main(String[] args) {
        WorldLoad.load();

        Map<RoomLocation, Room> result = mapWorld(WorldMap.getRoomMap());

        try {
            loadMudServerProperties();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        World.getInstance();
        World.getRooms().clear();
        World.getRooms().putAll(result);

        LOGGER.info("Good portal is: "+World.getPortal(true));
        LOGGER.info("Evil portal is: "+World.getPortal(false));
    }
}
