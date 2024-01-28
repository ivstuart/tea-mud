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

import java.util.ArrayList;

public class Room {

    private static final Logger LOGGER = LogManager.getLogger();
    private final GridLocation gridLocation;
    private final ArrayList<Exit> exits;
    private final RoomFlags roomFlags = new RoomFlags();
    private int roomNumber;
    private int zoneId;

    public Room(GridLocation gridLocation) {
        this.gridLocation = gridLocation;
        this.exits = new ArrayList<>(4);
        this.zoneId = JModePanel.getZone();
    }

    public Room(int x, int y, int z) {
        this(new GridLocation(x, y, z));
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        LOGGER.debug("Setting zone id:" + zoneId);
        this.zoneId = zoneId;
    }

    public RoomFlags getRoomFlags() {
        return roomFlags;
    }

    public boolean isNarrowPassageway() {
        return roomFlags.getFlag(RoomFlags.NARROW);
    }

    public void setNarrowPassageway(boolean narrowPassageway) {
        if (narrowPassageway) {
            roomFlags.setFlag(RoomFlags.NARROW);
        } else {
            roomFlags.removeFlag(RoomFlags.NARROW);
        }
    }

    public GridLocation getGridLocation() {
        return gridLocation;
    }

    public ArrayList<Exit> getExits() {
        return exits;
    }


    public boolean addExit(int facing, boolean bidirectional) {
        return this.addExit(null, facing, bidirectional);
    }

    public boolean addExit(String direction, int facing, boolean bidirectional) {

        GridLocation destination = gridLocation.goFacing(facing);

        if (destination.isOutsideOfZone(World.zone)) {
            LOGGER.info("Hit edge of zone");
            return false;
        }
        Exit exit;
        if (direction == null) {
            exit = new Exit(facing, destination);
        } else {
            exit = new Exit(direction, destination);
        }

        if (hasDirection(exit.getName())) {

            if (!Facing.isCustom(exit.getName())) {
                LOGGER.debug("Has direction already");
                return false;
            }
        }

        if (World.getRoom(destination) == null) {
            LOGGER.debug("Has no room in that direction");
            return false;
        }

        LOGGER.info("Adding exit:" + exit + " to room:" + this);
        exits.add(exit);

        if (bidirectional) {
            LOGGER.info("Adding path back also");
            Room destinationRoom = World.getRoom(destination);

            if (destinationRoom == null) {
                LOGGER.warn("No room to add opposite direction exit into");
                return true;
            }

            destinationRoom.addExit(Facing.reverse(facing), false);
        }

        return true;
    }

    public boolean join(Room destination) {
        if (destination.getGridLocation().isNextTo(this.gridLocation)) {

            String directionFwd = gridLocation.getDestinationExit(destination.getGridLocation());
            String directionBack = destination.getGridLocation().getDestinationExit(gridLocation);

            Exit exitFwd = new Exit(directionFwd, destination.getGridLocation());
            Exit exitBack = new Exit(directionBack, gridLocation);

            this.addExit(exitFwd);
            destination.addExit(exitBack);

            return true;
        }
        return false;
    }

    private boolean addExit(Exit exitFwd) {

        if (hasDirection(exitFwd.getName())) {
            return false;
        }

        exits.add(exitFwd);
        return true;
    }

    public boolean hasDirection(String direction) {
        for (Exit exit : exits) {
            if (exit.getName().equals(direction)) {
                return true;
            }
        }
        return false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return "Room{" +
                "gridLocation=" + gridLocation +
                ", exits=" + exits +
                ", roomFlags=" + roomFlags +
                ", roomNumber=" + roomNumber +
                ", zoneId=" + zoneId +
                '}';
    }

    public void joinNeighbours() {

        addExit(0, true);
        addExit(1, true);
        addExit(2, true);
        addExit(3, true);

    }

    public void toggleExit(String direction, int facing) {

        for (Exit exit : exits) {
            if (exit.getName().equals(direction)) {
                LOGGER.debug("removing exit in direction:" + direction);
                exits.remove(exit);
                return;
            }
        }
        LOGGER.debug("adding exit in direction:" + direction);


        this.addExit(direction, facing, false);
        //  this.addExit(facing, false);

    }


    public void addExit(Room previousRoom) {

        int dx = gridLocation.getX() - previousRoom.getGridLocation().getX();
        int dy = gridLocation.getY() - previousRoom.getGridLocation().getY();

        boolean bidirectional = JModePanel.isBidirectional();

        if (dx < 0) {
            previousRoom.addExit(0, bidirectional);
            return;
        }
        if (dx > 0) {
            previousRoom.addExit(2, bidirectional);
            return;
        }

        if (dy < 0) {
            previousRoom.addExit(1, bidirectional);
            return;
        }
        if (dy > 0) {
            previousRoom.addExit(3, bidirectional);
        }

    }

    public Exit getExit(String direction) {
        for (Exit exit : exits) {
            if (exit.getName().equals(direction)) {
                return exit;
            }
        }
        return null;
    }
}
