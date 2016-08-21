package com.ivstuart.tmud.state;

import com.ivstuart.tmud.common.ExitEnum;
import com.ivstuart.tmud.state.util.RoomManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 20/08/2016.
 */
public class RoomBuilder {
    private static final Logger LOGGER = Logger
            .getLogger(RoomBuilder.class);
    private String id;
    private String path;

    private RoomIdentifer roomId;
    private String roomPrefix;

    private RoomIdentifer roomIdStartFill;
    private RoomIdentifer roomIdEndFill;

    public void setId(String startId) {
        this.id = startId;
    }

    public void setPath(String path) {
        this.path = path;
        if (this.path.indexOf("x")>-1) {
            parseX();
        }
    }

    public void setRoomPrefix(String roomPrefix) {
        this.roomPrefix = roomPrefix;
    }

    public void parseX() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbNumber = new StringBuilder();
        boolean xFlag=false;
        int xNumber=0;
        int yNumber=0;
        int number=0;
        int counter=0; boolean lastCharacter=false;
        for (char aChar : path.toCharArray()) {
            sb.append(aChar);
            counter++;
            if (Character.isDigit(aChar)) {
                sbNumber.append(aChar);
                if (counter < path.length()) {
                    continue;
                }
                else {
                    lastCharacter=true;
                }
            }
            if (Character.isAlphabetic(aChar) || lastCharacter) {
                if (sbNumber.length() > 0) {
                    number = Integer.parseInt(sbNumber.toString());
                    sbNumber = new StringBuilder();
                }

                if (xFlag) {
                    yNumber = number;
                    xFlag=false;
                    String newString = "["+xNumber+"n"+yNumber+"e"+xNumber+"s"+yNumber+"w]";
                    int startPos = sb.length() - String.valueOf(xNumber).length() - 2 - String.valueOf(yNumber).length() ;
                    int endPos = sb.length() -1 ;
                    if (lastCharacter) {
                        startPos++;
                    }
                    sb.replace(startPos,endPos,newString);
                }

            }
            if (aChar == 'x') {
                xFlag=true;
                xNumber=number;
                continue;
            }
        }
        this.path = sb.toString();
    }

    public void setExecute(String notused) {

        LOGGER.debug("Building area using path :"+path);

        Room startRoom = World.getRoom(id);
        roomId = new RoomIdentifer();
        roomId.setRoomPrefix(roomPrefix);
        if (startRoom == null) {
            LOGGER.warn("No start room " + this.id + " aborting path create");
            return;
        }

        // DONE eeeennnnwwwwssss
        // 6ne6se4swneesw6nessee4nw23s

        // TODO 3n3x5 => 3n[3n5e3s5w]
        // 4n4e4s4w fill loop written [4n4e4s4w]
        boolean fillOn = false;
        roomIdStartFill = new RoomIdentifer();
        roomIdEndFill = new RoomIdentifer();
        roomIdStartFill.setRoomPrefix(roomPrefix);
        roomIdEndFill.setRoomPrefix(roomPrefix);
        int number = 1;
        StringBuilder sbNumber = new StringBuilder();
        for (char aChar : path.toCharArray()) {

            if (aChar == '[') {
                fillOn = true;
                continue;
            }
            if (aChar == ']' && fillOn) {
                doFillArea(startRoom);
                fillOn = false;
                roomIdStartFill = new RoomIdentifer();
                roomIdEndFill = new RoomIdentifer();
                roomIdStartFill.setRoomPrefix(roomPrefix);
                roomIdEndFill.setRoomPrefix(roomPrefix);
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
                RoomIdentifer destRoomId = null;
                ExitEnum exit = null;
                if (Character.isAlphabetic(aChar)) {
                    exit = ExitEnum.valueOf(String.valueOf(aChar));
                    destRoomId = getDestinationRoomId(roomId, exit);
                }

                // New room clone if does not already exist
                Room nextRoom = World.getRoom(destRoomId.toString());

                if (nextRoom == null) {
                    nextRoom = new Room((BasicThing) startRoom);
                    nextRoom.setType(startRoom.getType());
                    nextRoom.setId(destRoomId.toString());
                    World.add(nextRoom);
                }

                if (fillOn) {
                    storeMaxMinRoomId(roomId);
                }

                RoomManager.createExit(startRoom, exit.getDesc(), destRoomId.toString(), false);

                startRoom = nextRoom;
                roomId = destRoomId;
            }

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
                RoomIdentifer roomId = new RoomIdentifer(x, y);
                roomId.setRoomPrefix(roomPrefix);
                Room nextRoom = World.getRoom(roomId.toString());

                // TODO determine the edge and skip fill
                if (roomCounter % 2 == 1) {
                    if (nextRoom == null) {
                        nextRoom = new Room((BasicThing) startRoom);
                        nextRoom.setId(roomId.toString());
                        World.add(nextRoom);
                        addedRooms.add(nextRoom);
                    }
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
        RoomIdentifer roomId = RoomIdentifer.getRoomId(room);
        RoomIdentifer destRoomId = getDestinationRoomId(roomId, exit);
        RoomManager.createExit(room, exit.getDesc(), destRoomId.toString(), false);
    }

    private void storeMaxMinRoomId(RoomIdentifer roomId) {

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

    private RoomIdentifer getDestinationRoomId(RoomIdentifer roomId, ExitEnum exit) {
        return roomId.createNewRoomId(exit);
    }

}
