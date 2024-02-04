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

package com.ivstuart.tmud.state.places;

import com.ivstuart.tmud.common.ExitEnum;
import com.ivstuart.tmud.state.util.RoomManager;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 20/08/2016.
 */
@Deprecated
public class RoomBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private String id;
    private String path;

    private RoomIdentifier roomId;
    private String roomPrefix;

    private RoomIdentifier roomIdStartFill;
    private RoomIdentifier roomIdEndFill;

    private RoomLocation startLocation;

    public void setId(String startId) {
        this.id = startId;
    }

    public void setPath(String path) {
        this.path = path;
        if (this.path.contains("x")) {
            parseX();
        }
    }

    public void setRoomPrefix(String roomPrefix) {
        this.roomPrefix = roomPrefix;
    }

    /**
     * DONE 3n3x5 => 3n[3n5e3s5w]
     */
    public void parseX() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbNumber = new StringBuilder();
        boolean xFlag = false;
        int xNumber = 0;
        int yNumber = 0;
        int number = 0;
        int counter = 0;
        boolean lastCharacter = false;
        for (char aChar : path.toCharArray()) {
            sb.append(aChar);
            counter++;
            if (Character.isDigit(aChar)) {
                sbNumber.append(aChar);
                if (counter < path.length()) {
                    continue;
                } else {
                    lastCharacter = true;
                }
            }
            if (Character.isAlphabetic(aChar) || lastCharacter) {
                if (sbNumber.length() > 0) {
                    number = Integer.parseInt(sbNumber.toString());
                    sbNumber = new StringBuilder();
                }

                if (xFlag) {
                    yNumber = number;
                    xFlag = false;
                    String newString = "[" + xNumber + "n" + yNumber + "e" + xNumber + "s" + yNumber + "w]";
                    int startPos = sb.length() - String.valueOf(xNumber).length() - 2 - String.valueOf(yNumber).length();
                    int endPos = sb.length() - 1;
                    if (lastCharacter) {
                        startPos++;
                    }
                    sb.replace(startPos, endPos, newString);
                }

            }
            if (aChar == 'x') {
                xFlag = true;
                xNumber = number;
            }
        }
        this.path = sb.toString();
    }

    public void setExecute(String notused) {

        LOGGER.debug("Building area using path :" + path);

        Room startRoom = World.getRoom(startLocation);

        if (startRoom == null) {
            LOGGER.warn("Room id: " + startLocation + " not found!");
            return;
        }


        roomId = startRoom.getRoomLocation().getRoomIdentifer();

        if (roomId == null) {
            roomId = RoomIdentifier.getRoomId(startRoom);
        }

        roomId.setRoomPrefix(roomPrefix);


        // DONE eeeennnnwwwwssss
        // 6ne6se4swneesw6nessee4nw23s

        // DONE 3n3x5 => 3n[3n5e3s5w]
        // 4n4e4s4w fill loop written [4n4e4s4w]
        boolean fillOn = false;
        int number = 1;
        StringBuilder sbNumber = new StringBuilder();
        for (char aChar : path.toCharArray()) {

            if (aChar == '[') {
                fillOn = true;
                roomIdStartFill = new RoomIdentifier(roomId.getX(), roomId.getY());
                roomIdEndFill = new RoomIdentifier(roomId.getX(), roomId.getY());
                roomIdStartFill.setRoomPrefix(roomPrefix);
                roomIdEndFill.setRoomPrefix(roomPrefix);
                continue;
            }
            if (aChar == ']' && fillOn) {
                doFillArea(startRoom);
                fillOn = false;
                continue;
            }
            if (Character.isDigit(aChar)) {
                sbNumber.append(aChar);
                continue;
            }
            if (sbNumber.length() > 0 && Character.isAlphabetic(aChar)) {
                number = Integer.parseInt(sbNumber.toString());
                sbNumber = new StringBuilder();
            }

            for (int index = 1; index <= number; index++) {
                RoomIdentifier destRoomId = null;
                ExitEnum exit = null;
                if (Character.isAlphabetic(aChar)) {
                    exit = ExitEnum.valueOf(String.valueOf(aChar));
                    destRoomId = getDestinationRoomId(roomId, exit);
                }

                if (destRoomId == null) {
                    LOGGER.warn("Null destRoomId skipping creating this exit");
                    continue;
                }

                RoomLocation roomLocation = new RoomLocation(destRoomId.getX(), destRoomId.getY(), destRoomId.getZ());

                Room nextRoom = new Room(roomLocation);
                nextRoom.setType(startRoom.getType());
                nextRoom.setId(destRoomId.toString());

                World.add(roomLocation, nextRoom);

                if (fillOn) {
                    storeMaxMinRoomId(roomId);
                }

                if (exit == null) {
                    LOGGER.warn("Null exit skipping creating this exit");
                    continue;
                }

                RoomLocation destinationRoomLocation = new RoomLocation(destRoomId.getX(), destRoomId.getY(), destRoomId.getZ());


                RoomManager.createExit(startRoom, exit.getDesc(), destinationRoomLocation, false);

                startRoom = nextRoom;
                roomId = destRoomId;
            }
            number = 1;

        }
    }

    private void doFillArea(Room startRoom) {
        LOGGER.info("Filling area from " + roomIdStartFill + " to " + roomIdEndFill);
        List<Room> addedRooms = new ArrayList<>();
        for (int y = roomIdStartFill.getY(); y < roomIdEndFill.getY(); y++) {
            int roomCounter = 0;
            Room previousRoom = null;
            for (int x = roomIdStartFill.getX(); x < roomIdEndFill.getX(); x++) {
                // Find first edge with a filled room.
                RoomIdentifier roomId = new RoomIdentifier(x, y);
                roomId.setRoomPrefix(roomPrefix);
                Room nextRoom = null;
                // Note determine the edge and skip fill
                // this is basically a polygon fill for rooms
                if (roomCounter % 2 == 1) {

                    nextRoom = new Room(new RoomLocation(x, y, roomIdStartFill.getZ()));
                    nextRoom.setId(roomId.toString());
                    World.add(nextRoom.getRoomLocation(), nextRoom);
                    addedRooms.add(nextRoom);

                }

                if (nextRoom != null && previousRoom == null) {
                    roomCounter++;
                }
                previousRoom = nextRoom;
            }
        }
        LOGGER.info("Filling area with " + addedRooms.size() + " new rooms with nsew exits");

        // Defer adding the exits to ensure that the rooms have been created.
        for (Room room : addedRooms) {

            // Add N S E W exits
            addExitToRoom(room, ExitEnum.n);
            addExitToRoom(room, ExitEnum.s);
            addExitToRoom(room, ExitEnum.w);
            addExitToRoom(room, ExitEnum.e);
        }

    }

    private void addExitToRoom(Room room, ExitEnum exit) {
        RoomIdentifier roomId = RoomIdentifier.getRoomId(room);
        RoomIdentifier destRoomId = getDestinationRoomId(roomId, exit);

        RoomLocation destinationRoomLocation = new RoomLocation(destRoomId.getX(), destRoomId.getY(), destRoomId.getZ());

        RoomManager.createExit(room, exit.getDesc(), destinationRoomLocation, false);
    }

    private void storeMaxMinRoomId(RoomIdentifier roomId) {

        // New minimum X
        if (roomId.getX() < roomIdStartFill.getX()) {
            roomIdStartFill.setX(roomId.getX());
        }

        // New max X
        if (roomId.getX() > roomIdEndFill.getX()) {
            roomIdEndFill.setX(roomId.getX());
        }

        // New min Y
        if (roomId.getY() < roomIdStartFill.getY()) {
            roomIdStartFill.setY(roomId.getY());
        }

        // New max Y
        if (roomId.getY() > roomIdEndFill.getY()) {
            roomIdEndFill.setY(roomId.getY());
        }

    }

    private RoomIdentifier getDestinationRoomId(RoomIdentifier roomId, ExitEnum exit) {
        return roomId.createNewRoomId(exit);
    }

    public void setStartLocation(RoomLocation startLocation) {
        this.startLocation = startLocation;
    }
}
