package com.ivstuart.tmud.poc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Room {

    private static final Logger LOGGER = LogManager.getLogger();

    private int roomNumber;

    private final GridLocation gridLocation;

    private final ArrayList<Exit> exits;
    private boolean isNarrowPassageway = true;

    public RoomFlags getRoomFlags() {
        return roomFlags;
    }

    private final RoomFlags roomFlags = new RoomFlags();

    public Room(GridLocation gridLocation) {
        this.gridLocation = gridLocation;
        exits = new ArrayList<>(4);
    }

    public Room(int x, int y, int z) {
        this(new GridLocation(x,y,z));
    }

    public boolean isNarrowPassageway() {
        return isNarrowPassageway;
    }

    public void setNarrowPassageway(boolean narrowPassageway) {
        isNarrowPassageway = narrowPassageway;
    }

    public GridLocation getGridLocation() {
        return gridLocation;
    }

    public ArrayList<Exit> getExits() {
        return exits;
    }

    public boolean addExit(int facing, boolean bidirectional) {

        GridLocation destination = gridLocation.goFacing(facing);

        if (destination.isOutsideOfZone(World.zone)) {
            LOGGER.info("Hit edge of zone");
            return false;
        }

        Exit exit = new Exit(facing, destination);

        if (hasDirection(exit.getName())) {
            LOGGER.debug("Has direction already");
            return false;
        }

        if (World.getRoom(destination) == null) {
            LOGGER.debug("Has no room in that direction");
            return false;
        }

        LOGGER.info("Adding exit:"+exit+" to room:"+this);
        exits.add(exit);

        if (bidirectional) {
            LOGGER.info("Adding path back also");
            Room destinationRoom = World.getRoom(destination);

            if (destinationRoom == null) {
                LOGGER.warn("No room to add opposite direction exit into");
                return true;
            }

            destinationRoom.addExit(reverseFacing(facing), false);
        }

        return true;
    }

    public boolean join(Room destination) {
        if (destination.getGridLocation().isNextTo(this.gridLocation)) {

            String directionFwd = gridLocation.getDestinationExit(destination.getGridLocation());
            String directionBack = destination.getGridLocation().getDestinationExit(gridLocation);

            Exit exitFwd = new Exit(directionFwd,destination.getGridLocation());
            Exit exitBack = new Exit(directionBack,gridLocation);

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

    public static int reverseFacing(int facing) {
        return (facing + 2) % 4;
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
                "roomNumber=" + roomNumber +
                ", gridLocation=" + gridLocation +
                ", exits=" + exits +
                ", isNarrowPassageway=" + isNarrowPassageway +
                ", roomFlags=" + roomFlags +
                '}';
    }

    public void joinNeighbours() {

        addExit(0,true);
        addExit(1,true);
        addExit(2,true);
        addExit(3,true);

    }

    public void toggleExit(String direction, int facing) {

        for (Exit exit : exits) {
            if (exit.getName().equals(direction)) {
                LOGGER.debug("removing exit in direction:"+direction);
                exits.remove(exit);
                return;
            }
        }
        LOGGER.debug("adding exit in direction:"+direction);
        this.addExit(facing, false);

    }

    public void toggleExit(int i) {
    }

    public void addExit(Room previousRoom) {

        int dx = gridLocation.getX() - previousRoom.getGridLocation().getX();
        int dy = gridLocation.getY() - previousRoom.getGridLocation().getY();

        boolean onway = !JModePanel.isOneWay();

        if (dx < 0) { previousRoom.addExit(0,onway); return ;}
        if (dx > 0 ) { previousRoom.addExit(2,onway); return ;}

        if (dy < 0) { previousRoom.addExit(1,onway); return ;}
        if (dy > 0 ) { previousRoom.addExit(3,onway); return ;}

    }
}
