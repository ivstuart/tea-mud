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

package com.ivstuart.tmud.state.util;

import com.ivstuart.tmud.constants.DoorState;
import com.ivstuart.tmud.state.places.Door;
import com.ivstuart.tmud.state.places.Exit;
import com.ivstuart.tmud.state.places.Room;
import com.ivstuart.tmud.state.dao.DoorDAO;
import com.ivstuart.tmud.state.places.RoomLocation;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.ivstuart.tmud.constants.DoorState.*;

@Deprecated
public class RoomManager {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final List<DoorDAO> exitsWithDoors = new ArrayList<>();
    private static Door lastCreatedDoor;

    public static void createDoors(RoomLocation roomId_, String exit_) {

        LOGGER.info("Creating door in room [ " + roomId_ + " ] for [ " + exit_
                + " ]");

        DoorState state = OPEN;

        if (exit_.contains("[")) {
            exit_ = exit_.substring(1, exit_.length() - 1);
            state = CLOSED;
        } else if (exit_.contains("{")) {
            exit_ = exit_.substring(1, exit_.length() - 1);
            state = LOCKED;
        }

        Door aDoor = new Door();

        aDoor.setLook("gate");
        aDoor.setState(state);

        lastCreatedDoor = aDoor;

        DoorDAO doorDAO = new DoorDAO();
        doorDAO.setDoor(aDoor);
        doorDAO.setExit(exit_);
        doorDAO.setRoomLocation(roomId_);

        exitsWithDoors.add(doorDAO);

    }


    public static void createExit(Room from, String direction, RoomLocation toRoomId) {
        createExit(from, direction, toRoomId, false);

    }


    public static void createExit(Room from, String direction, RoomLocation toRoomId,
                                  boolean isOneWay) {

        Exit exitFromTo = new Exit(direction, toRoomId);
        from.add(exitFromTo);

        if (isOneWay) {
            return;
        }

        Exit exitToFrom = new Exit(reverseDirection(direction), from.getRoomLocation());

        Room room = World.getRoom(toRoomId);

        if (room != null) {
            room.add(exitToFrom);
        }

    }

    public static String reverseDirection(String direction) {
        switch (direction) {
            case "east":
                return "west";
            case "south":
                return "north";
            case "west":
                return "east";
            case "north":
                return "south";
            case "up":
                return "down";
            case "down":
                return "up";
        }
        return direction;
    }


    public static void setDoorKeys(String keys_) {
        if (lastCreatedDoor == null) {
            return;
        }

        lastCreatedDoor.setKeyId(keys_);
    }

    public static void setDoorOnEndOfExit() {

        for (DoorDAO door : exitsWithDoors) {

            LOGGER.debug("Setting up [" + door + "]");

            Room room = World.getRoom(door.getRoomLocation());

            Exit exit = room.getExit(door.getExit());

            exit.setDoor(door.getDoor());

            Exit opExit = exit.getDestinationRoom().getExit(
                    RoomManager.reverseDirection(door.getExit()));

            if (opExit != null) {
                opExit.setDoor(exit.getDoor());
            }
        }

        exitsWithDoors.clear();
    }

    public static void setDoorBashable(boolean flag) {
        lastCreatedDoor.setBashable(flag);
    }

    public static void setDoorPickable(boolean flag) {
        lastCreatedDoor.setPickable(flag);
    }

    public static void setDoorUnspellable(boolean flag) {
        lastCreatedDoor.setUnspellable(flag);
    }

    public static void setDoorStrength(int str) {
        lastCreatedDoor.setStrength(str);
    }

    public static void setDoorDifficulty(int doorDifficulty) {
        lastCreatedDoor.setDifficulty(doorDifficulty);
    }
}
